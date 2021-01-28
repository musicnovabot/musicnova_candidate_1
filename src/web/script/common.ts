import '../style/styles.sass'
//import _ from '../../../musicnova-web-shared/build/distributions/musicnova-web-shared'


// @ts-ignore
export const version = MUSICNOVA_VERSION

const data = {
    // @ts-ignore
    version: MUSICNOVA_VERSION
}

export default data

export function printStartData() {
    console.log(data)
}

/**
 * lazy load because kotlin becomes large very likely
 */
export async function loadKotlinPart(){
    await import("../../../musicnova-web-shared/build/distributions/musicnova-web-shared")
    // @ts-ignore
    return window.kotlinPart
}