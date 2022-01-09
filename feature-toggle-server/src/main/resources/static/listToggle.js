function createViewToggles(toggles) {
    const list = document.querySelector(".toggles-list")
    if (list !== null) {
        list.remove()
    }

    console.log(toggles)

    let listNew = '<div class="toggles-list">'
    toggles.featureToggles.forEach(t => {
        listNew = listNew + createViewToggle(t)
    })
    listNew = listNew + '</div>'

    const togglesElement = document.querySelector('.toggles')

    togglesElement.insertAdjacentHTML('beforeend', listNew)
}

function createViewToggle(toggle) {
    console.log(toggle)

    return `
                    <div class="box view-${toggle.id}">
                        <p><strong>${toggle.name}</strong></p>
                        <p><strong>Включена</strong>: ${toggle.enabled}</p>
                        <p><strong>Дата начала</strong>: ${toggle.startDate}</p>
                        <p><strong>Дата окончания</strong>: ${toggle.endDate}</p>
                        <p><strong>Тип</strong>: ${toggle.type}</p>
                        ${createViewCondition(toggle.condition)}

                        <a class="button is-info" onclick="showHideEdit('${toggle.id}')">Редактировать</a>
                        <a class="button is-danger" onclick="deleteToggle('${toggle.id}')">Удалить</a>
                    
                        <div class="box edit-${toggle.id}" style="display: none">${createHtmlEditToggle(toggle)}</div>
                    </div>
           `
}

function createHtmlEditToggle(toggle) {
    return editToggleHtml(toggle)
}

function showHideEdit(toggleId) {
    const clazz = '.edit-' + toggleId
    console.log(clazz)
    const element = document.querySelector(clazz)
    console.log(element)
    if (element !== null) {
        element.style.display = element.style.display === 'none' ? 'block' : 'none'
    }
}

function createViewCondition(condition) {
    let html = ""
    let conditionHtml = ""
    if (condition !== null) {

        if (
            condition.parameters !== null
            && condition.parameters.parameters !== null
            && condition.parameters.parameters.length !== 0
        ) {
            let ht = "<p><strong>Параметры</strong></p>"
            condition.parameters.parameters.forEach(v => ht = ht + `<p>${v.name}: ${v.description}</p>`)

            ht = ht + `<p><strong>Условие</strong>:${condition.condition.body}</p>`

            conditionHtml = ht
        }

        html =
            `
                    <div class="box">
                        <p><strong>Описание условия</strong></p>
                        <p><strong>Тип и язык</strong>: ${condition.type} ${condition.language}</p>
                        ${conditionHtml}
                    </div>
                `
    }
    return html
}

function deleteToggle(id) {
    $.ajax({
        url: '/api/v1/feature-toggles/' + id,
        type: "DELETE",
        contentType: "application/json; charset=utf-8",
        success: function (data, status, object) {
            const element = document.querySelector('.view-' + id)
            if (id !== null) {
                element.remove()
            }
        },
        error: function (answer) {
            showModal(`Ошибка удаления feature toggles с id '${id}'`)
        }
    })
}

function loadFeatureToggles() {
    $.ajax({
        url: '/api/v1/feature-toggles',
        type: "GET",
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function (data, status, object) {
            createViewToggles(data)
        },
        error: function (answer) {
            showModal(`Ошибка получения feature toggles`)
        }
    })
}

const testToggles = {
    "featureToggles": [
        {
            "id": "c2156d81-c1c5-40c1-9a4e-71ad794dcf1d",
            "name": "sasdbasasb",
            "enabled": false,
            "startDate": "2021-12-12T12:12:12",
            "endDate": "2021-12-12T12:12:12",
            "type": "OPERATIONAL",
            "condition": null
        },
        {
            "id": "b29dbeda-b2ff-4dc8-8019-ce169c932f0d",
            "name": "asdasdv",
            "enabled": false,
            "startDate": "2021-12-12T12:12:12",
            "endDate": "2021-12-12T12:12:12",
            "type": "OPERATIONAL",
            "condition": {
                "type": "EXPRESSION",
                "language": "JAVA",
                "parameters": {
                    "parameters": [
                        {
                            "name": "12",
                            "description": "12"
                        }
                    ]
                },
                "condition": {
                    "body": "Integer.valueOf(\"12\") > 12"
                }
            }
        }
    ]
}

createViewToggles(testToggles)
// loadFeatureToggles()