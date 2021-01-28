import {loadKotlinPart} from "./common";

async function loadBulmaJs() {
    let bulma = (await import('@vizuaalog/bulmajs')).default;
    console.info("BulmaJS: ", bulma.VERSION, bulma)
}


async function loadBarbaJS() {
    let barba = (await import('@barba/core')).default
    barba.init({
        logLevel: "info",
        prefetchIgnore: true,
        cacheIgnore: true,
    })
}

async function loadPaceJS() {
    let pace = (await import('./lib/pace'))
    console.log('pace', pace)
}

export default async function loadPageScripts() {
    await loadKotlinPart()
    await loadBulmaJs()
    await loadBarbaJS()
    await loadPaceJS()

}