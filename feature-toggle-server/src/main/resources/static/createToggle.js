function createParamElement() {
    const createParams = document.querySelector('.create-params')
    const childId = 'create-param-id-' + createParams.childElementCount + 1
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
                        <input class="input toggle-creation-condition-parameter-name" type="text" placeholder="test">
                    </div>
                </div>
                <div class="field">
                    <label class="label">Описание параметра</label>
                    <div class="control">
                        <input class="input toggle-creation-condition-parameter-description" type="text"
                               placeholder="Тестовое число между 0 и 10">
                    </div>
                </div>
            </div>
            `

    createParams.insertAdjacentHTML('beforeend', param)
}

function createCondition() {
    const idCondition = "condition-add-id"

    const condition =
        `
                <div class="box" id="${idCondition}">
                    <div class="field">
                        <label class="label">Тип и язык условия</label>
                        <div class="select">
                            <select class="toggle-creation-condition-type-language">
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
                            <p class="level-item"><a class="button" onclick="createParamElement(this)">Добавить параметр</a></p>
                        </div>
                    </nav>
                    <div class="create-params"></div>

                    <div class="field">
                        <label class="label">Условие, написанное на выбранном языке</label>
                        <div class="control">
                            <input class="input toggle-creation-condition" type="text" placeholder="Integer.valueOf(test) > 5">
                        </div>
                    </div>

                    <div class="field">
                        <div class="control">
                            <a class="button is-warning" onclick="deleteElementById('${idCondition}')">Удалить условие</a>
                        </div>
                    </div>
                </div>
            `

    const createCondition = document.querySelector('.create-condition')
    if (createCondition.childElementCount === 0) {
        createCondition.insertAdjacentHTML('beforeend', condition)
    }
}

function createEditOrCreateToggleHtml(idElement) {
    return `
                    <div class="container create-toggle" id="${idElement}">
                        <nav class="level">
                            <div class="level-left">
                                <div class="level-item">
                                    <p class="subtitle is-5">
                                        <strong>Создание Feature Toggles</strong>
                                    </p>
                                </div>
                            </div>
                            <div class="level-right">
                                <p class="level-item"><a class="button" onclick="deleteElementById('${idElement}')">Закрыть</a></p>
                            </div>
                        </nav>


                        <form class="box">
                            <div class="field">
                                <label class="label">Имя (должно быть уникально)</label>
                                <div class="control">
                                    <input class="input toggle-creation-name" type="text" placeholder="Имя">
                                </div>
                            </div>

                            <label class="checkbox">
                                <input class="toggle-creation-enabled" type="checkbox">
                                Включен
                            </label>

                            <div class="field">
                                <label class="label">Дата начала</label>
                                <div class="control">
                                    <input class="input toggle-creation-start-date" type="text" placeholder="yyyy-mm-ddThh:mm:ss">
                                </div>
                            </div>

                            <div class="field">
                                <label class="label">Дата окончания</label>
                                <div class="control">
                                    <input class="input toggle-creation-end-date" type="text" placeholder="yyyy-mm-ddThh:mm:ss">
                                </div>
                            </div>

                            <div class="field">
                                <label class="label">Описание</label>
                                <div class="control">
                                    <input class="input toggle-creation-description" type="text" placeholder="Для чего используется">
                                </div>
                            </div>

                            <div class="field">
                                <label class="label">Тип</label>
                                <div class="select">
                                    <select class="toggle-creation-type">
                                        <option>OPERATIONAL</option>
                                    </select>
                                </div>
                            </div>

                            <div class="field">
                                <div class="control">
                                    <a class="button is-info" onclick="createCondition()">Добавить условие</a>
                                </div>
                            </div>
                            <div class="create-condition field"></div>

                            <div class="field">
                                <div class="control">
                                    <a class="button is-link" onclick="createToggleOnServer()">Создать</a>
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

function createToggleOnServer() {
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