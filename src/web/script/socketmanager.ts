import {loadKotlinPart} from "./common";

const socketPath = "/internal/socket"

let socket: WebSocket
let isBinary: boolean
let kotlinPart
let managedConnection

export async function startSocket() {
    kotlinPart = await loadKotlinPart()
    try {
        socket = await startNativeWS()
        isBinary = true
    } catch (e) {
        console.warn("native ws didnt work! fallback to sockjs", e)
        socket = await startSockJs()
        isBinary = false
    }
    managedConnection = kotlinPart.manageConnection(socket, isBinary)
    await postStarted()
}


export async function stopSocket() {
    socket.close()
}

function wsUrl(): string {
    let proto: String
    if (location.protocol === 'https:')
        proto = "wss://"
    else
        proto  = "ws://"
    return proto+location.host+socketPath
}

async function startNativeWS(): Promise<WebSocket> {
    let ws = new WebSocket(wsUrl())
    return new Promise((resolve, reject) => {
        ws.onopen = function () {
            resolve(ws)
        }
        ws.onerror = function (event) {
            reject(event)
        }
    })
}

async function startSockJs(): Promise<WebSocket> {
    let SockJS = (await import( 'sockjs-client/dist/sockjs')).default
    let sock = new SockJS(socketPath)
    return new Promise(((resolve, reject) => {
        sock.onopen = function () {
            resolve(sock)
        }
        sock.onerror = function (event) {
            reject(event)
        }
    }))

}

async function postStarted() {
    sendPacket(kotlinPart.packetBuilder.buildPacketPlayerUpdateTrack("hello there"))
    console.log("packet sended")
}

export function sendPacket(packet) {
    managedConnection.send(packet)
}