function deleteElementById(id) {
    console.log(id)
    document.querySelector('#' + id).remove()
}

function showModal(text) {
    const html =
        `
                <div class="modal is-active">
                    <div class="modal-background""></div>
                    <div class="modal-content">
                        <div class="card">
                            <div class="card-content">
                                <div class="content modal-text">
                                ${text}
                                </div>
                            </div>
                        </div>
                    </div>
                    <button class="modal-close is-large" aria-label="close"></button>
                </div>
            `

    const elementPlaceModal = document.querySelector('.modal-message')
    const elementModalOld = document.querySelector('.modal')
    if (elementModalOld !== null) {
        elementModalOld.remove()
    }

    elementPlaceModal.insertAdjacentHTML('beforeend', html)
    const elementModalNew = document.querySelector('.modal')

    const back = document.querySelector('.modal-background')
    const close = document.querySelector('.modal-close')
    const fun = function () {
        elementModalNew.remove()
    }
    back.addEventListener('click', fun)
    close.addEventListener('click', fun)
}