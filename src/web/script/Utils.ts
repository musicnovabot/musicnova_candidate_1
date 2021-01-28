import Swal from "sweetalert2";
import {clearCache} from "clear-cache"

export function showError(error){
    Swal.fire({
        title: 'Error',
        icon: 'error',
        confirmButtonText: 'Reload',
        text: error.toLocaleString()
    }).then(() => {
        try {
            /* try fore reload as long as possible */
            window.location.reload(true)
        } catch (e) {
            window.location.reload()
        }
    })
}