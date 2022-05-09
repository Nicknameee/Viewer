async function checkSessionValidity() {
    let request = await fetch('/api/manager/session/valid', {method: 'get'});
    let data = await request.text()
    let json = await JSON.parse(data)
    return await json.valid
}