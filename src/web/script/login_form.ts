import axios, {AxiosResponse} from "axios";
import Swal from 'sweetalert2'

import {stringify as stringifyQuery} from "querystring"
import {showError} from "./Utils";

import Cleave from "cleave.js"

async function sendLoginRequest(username: string, password: string, otp: string | null): Promise<AxiosResponse> {
    let formData = new URLSearchParams()
    formData.append("username", username)
    formData.append("password", password)
    if (otp != null) formData.append("otp", otp)
    return axios.post("/internal/login", stringifyQuery({
        username: username,
        password: password
    }), {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    })
}

function setInputElementsEnabled(elements: Array<HTMLInputElement>, enabled: boolean) {
    elements.forEach((element) => {
        element.disabled = !enabled
    })
}

function onLoginSuccess() {
    location.href = "/"
}

export function appendToLoginForm() {

    let loginForm = document.querySelector<HTMLFormElement>('#loginForm')!
    let usernameInput = document.querySelector<HTMLInputElement>("#input_username")!
    let passwordInput = document.querySelector<HTMLInputElement>("#input_password")!
    let loginButton = document.querySelector<HTMLInputElement>("#input_password")!
    let elements = [usernameInput!, passwordInput!, loginButton!]

    loginForm!.onsubmit = (event) => {
        event.preventDefault()
        let username = usernameInput!.value
        let password = passwordInput!.value


        function onWrongCredentials() {

        }

        function onRequireOTP() {
            let fieldDiv = document.createElement("div")
            fieldDiv.classList.add("field")
            let controlDiv = document.createElement("div")
            controlDiv.classList.add("control")
            fieldDiv.appendChild(controlDiv)
            let fieldInput = document.createElement("input")
            fieldInput.type = "text"
            fieldInput.classList.add("input")
            controlDiv.appendChild(fieldInput)
            let formated = new Cleave(fieldInput, {
                blocks: [3, 3],
                delimiter: ' ',
                numericOnly: true
            })

            Swal.fire({
                title: '2FA Key',
                html: fieldDiv,
                showCloseButton: true,
                confirmButtonText: 'Send'
            }).then((result) => {
                formated.destroy()
                if (result.isConfirmed) {
                    fieldInput.disabled = true
                    sendRequestAndHandleResult(fieldInput.value)
                } else {
                    setInputElementsEnabled(elements, true)
                    passwordInput.value = ""
                }
            })
        }

        function sendRequestAndHandleResult(otp: string | null = null) {
            setInputElementsEnabled(elements, false)
            sendLoginRequest(username, password, otp).then((response) => {
                console.log(response)
                let status = response.data.status
                switch (status) {
                    case "SUCCESS":
                        onLoginSuccess()
                        break
                    case "WRONG_CREDENTIALS":
                        onWrongCredentials()
                        break
                    case "REQUIRE_OTP":
                        onRequireOTP()
                        break
                    case "BLOCKED":
                        break

                }
                //usernameInput!.disabled = false
                //passwordInput!.disabled = false

            }).catch((error) => {
                showError(error)
            })
        }

        try {
            sendRequestAndHandleResult()
        } catch (e) {
            showError(e)
        }

    }
}
