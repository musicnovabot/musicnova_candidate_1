import {loadKotlinPart, printStartData} from "../common";

import '../../style/dashboard.sass'
import loadPageScripts from "../3rdParty";
import {startSocket} from "../socketmanager";


console.log("main script")
printStartData()
const pageScriptsPomise = loadPageScripts()
document.addEventListener('DOMContentLoaded', function (event) {
    async function exec() {
        console.log("page loaded, waiting for additional scripts...")
        console.time("loadScripts")
        await pageScriptsPomise
        console.timeEnd("loadScripts")
        console.log("additional scripts loaded.")
        console.time("socket-start")
        await startSocket()
        console.timeEnd("socket-start")
    }

    exec().then()
})
