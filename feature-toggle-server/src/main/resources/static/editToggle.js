function createParamElementForEdit(idToggle) {
    const createParams = document.querySelector('.create-params-edit' + idToggle)
    const childId = 'create-param-id-' + createParams.childElementCount + 1 + idToggle
    const param = `
            <div class="create-param box" id="${childId}">
                <nav class="level">
                    <div class="level-left">
                        <p class="level-item"><a class="button" onclick="deleteElementById('${childId}')">Удалить параметр</a></p>
                    </div>
                </nav>
                <div class="field">
                    <label class="label">Имя параметра</label>
                    <div class="control">
                        <input class="${'input toggle-creation-condition-parameter-name' + idToggle}" type="text" placeholder="test">
                    </div>
                </div>
                <div class="field">
                    <label class="label">Описание параметра</label>
                    <div class="control">
                        <input class="${'input toggle-creation-condition-parameter-description' + idToggle}" type="text"
                               placeholder="Тестовое число между 0 и 10">
                    </div>
                </div>
            </div>
            `

    createParams.insertAdjacentHTML('beforeend', param)
}

function editCondition(idToggle) {
    const idCondition = "condition-edit-" + idToggle

    const html = `
                <div class="box" id="${idCondition}">
                    <div class="field">
                        <label class="label">Тип и язык условия</label>
                        <div class="select">
                            <select class="${'toggle-creation-condition-type-language' + idToggle}">
                                <option>EXPRESSION JAVA</option>
                            </select>
                        </div>
                    </div>

                    <nav class="level">
                        <div class="level-left">
                            <div class="level-item">
                                <p class="subtitle is-6">
                                    <strong>Параметры для условия</strong>
                                </p>
                            </div>
                        </div>
                        <div class="level-right">
                            <p class="level-item"><a class="button" onclick="createParamElementForEdit('${idToggle}')">Добавить параметр</a></p>
                        </div>
                    </nav>
                    <div class="create-params-edit"></div>

                    <div class="field">
                        <label class="label">Условие, написанное на выбранном языке</label>
                        <div class="control">
                            <input class="${'input toggle-creation-condition' + idToggle}" type="text" placeholder="Integer.valueOf(test) > 5">
                        </div>
                    </div>

                    <div class="field">
                        <div class="control">
                            <a class="button is-warning" onclick="deleteElementById('${idCondition}')">Удалить условие</a>
                        </div>
                    </div>
                </div>
            `

    const editCondition = document.querySelector('.edit-condition-' + idToggle)
    if (editCondition.childElementCount === 0) {
        editCondition.insertAdjacentHTML('beforeend', html)
    }
}

function editToggleHtml(toggle) {
    const idToggle = toggle.id

    return `
                    <div class="container ${'edit-toggle' + idToggle}">
                        <nav class="level">
                            <div class="level-left">
                                <div class="level-item">
                                    <p class="subtitle is-5">
                                        <strong>Редактирование Feature Toggles</strong>
                                    </p>
                                </div>
                            </div>
                            <div class="level-right">
                                <p class="level-item"><a class="button" onclick="showHideEdit('${idToggle}')">Закрыть</a></p>
                            </div>
                        </nav>


                        <form class="box">
                            <div class="field">
                                <label class="label">Имя (должно быть уникально)</label>
                                <div class="control">
                                    <input class="${'input toggle-creation-name' + idToggle}" type="text" placeholder="Имя" value="${toggle.name}">
                                </div>
                            </div>

                            <label class="checkbox">
                                <input class="${'toggle-creation-enabled' + idToggle}" type="checkbox" checked="${toggle.enabled}">
                                Включен
                            </label>

                            <div class="field">
                                <label class="label">Дата начала</label>
                                <div class="control">
                                    <input class="${'input toggle-creation-start-date' + idToggle}" type="text" placeholder="yyyy-mm-ddThh:mm:ss" value="${toggle.startDate}">
                                </div>
                            </div>

                            <div class="field">
                                <label class="label">Дата окончания</label>
                                <div class="control">
                                    <input class="${'input toggle-creation-end-date' + idToggle}" type="text" placeholder="yyyy-mm-ddThh:mm:ss" value="${toggle.endDate}">
                                </div>
                            </div>

                            <div class="field">
                                <label class="label">Описание</label>
                                <div class="control">
                                    <input class="${'toggle-creation-condition-type-language' + idToggle}" type="text" placeholder="Для чего используется" value="Заглушка">
                                </div>
                            </div>

                            <div class="field">
                                <label class="label">Тип</label>
                                <div class="select">
                                    <select class="${'toggle-creation-type' + idToggle}">
                                        <option>${toggle.type}</option>
                                    </select>
                                </div>
                            </div>

                            <div class="field">
                                <div class="control">
                                    <a class="button is-info" onclick="editCondition('${idToggle}')">Добавить условие</a>
                                </div>
                            </div>
                            <div class="${'edit-condition-' + idToggle} field"></div>

                            <div class="field">
                                <div class="control">
                                    <a class="button is-link" onclick="editToggleOnServer('${idToggle}')">Сохранить</a>
                                </div>
                            </div>
                        </form>
                    </div>
                `

}

function insertCreateToggle() {
    const createTogglePlaceElement = document.querySelector('.place-create-toggle')
    if (createTogglePlaceElement.childElementCount === 0) {
        const id = 'create-toggle-id'
        createTogglePlaceElement.insertAdjacentHTML('beforeend', createEditOrCreateToggleHtml(id))
    }
}

function editToggleOnServer(idToggle) {
    const name = document.querySelector('.toggle-creation-name')?.value
    const enabled = document.querySelector('.toggle-creation-enabled')?.checked
    const startDate = document.querySelector('.toggle-creation-start-date')?.value
    const endDate = document.querySelector('.toggle-creation-end-date')?.value
    const description = document.querySelector('.toggle-creation-description')?.value
    const type = document.querySelector('.toggle-creation-type')?.value

    const conditionTypeLanguage = document.querySelector('.toggle-creation-condition-type-language')?.value
    const condition = document.querySelector('.toggle-creation-condition')?.value

    const conditionParams = document.querySelectorAll('.create-param')
    const paramsList = []
    conditionParams.forEach(params => {
            const paramName = params.querySelector('.toggle-creation-condition-parameter-name')?.value
            const paramDescription = params.querySelector('.toggle-creation-condition-parameter-description')?.value

            paramsList.push({name: paramName, description: paramDescription})
        }
    )

    console.log(paramsList)

    // console.log(name)
    // console.log(enabled)
    // console.log(startDate)
    // console.log(endDate)
    // console.log(description)
    // console.log(type)

    // console.log(conditionType)
    // console.log(conditionLanguage)
    // console.log(condition)
    // console.log(conditionParams)

    let conditionObject
    if (conditionTypeLanguage === undefined ||
        condition === undefined || condition === ""
    ) {
        conditionObject = null
    } else {
        const typeAndLang = conditionTypeLanguage.split(' ')
        conditionObject = {
            type: typeAndLang[0],
            language: typeAndLang[1],
            parameters: {
                inputParameters: paramsList
            },
            condition: {
                body: condition
            }
        }
    }

    //http://localhost:8080/api/v1/feature-toggles

    const data = {
        name: name,
        enabled: enabled,
        startDate: startDate,
        endDate: endDate,
        description: description,
        type: type,
        condition: conditionObject
    }

    console.log(data)

    $.ajax({
        url: '/api/v1/feature-toggles',
        type: "POST",
        data: JSON.stringify(data),
        contentType: "application/json; charset=utf-8",
        success: function (response) {
            console.log(response)
            loadFeatureToggles()
        },
        error: function (one, two, three) {
            console.log(one)
            console.log(two)
            console.log(three)
            showModal(`Создание 'Feature Toggle' с именем  '${name}' завершилось с ошибкой.`)
        }
    })
}