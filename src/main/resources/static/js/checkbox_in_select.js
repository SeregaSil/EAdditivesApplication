/* SELECT
=============================*/
const selectHeader = document.querySelector('.select');

//SELECT__HEADER
document.addEventListener('click', selectToggle);
document.addEventListener('keydown', selectToggleEnter);

function selectToggle(event) {
    if (event.target.closest('.select__header')) {
        selectHeader.classList.toggle('is-active');
        spoilerDefault();
    }

    if (!event.target.closest('.form__checkbox')) {
        selectHeader.classList.remove('is-active');
        spoilerDefault();
    }
}

function selectToggleEnter(event) {
    if ((event.code === 'Enter') && event.target.closest('.select__header')) {
        selectHeader.classList.toggle('is-active');
        spoilerDefault();
    }

    if ((event.code === 'Enter') && !event.target.closest('.form__checkbox')) {
        selectHeader.classList.remove('is-active');
        spoilerDefault();
    }
}

//CHECKBOX
const checkboxForm = document.querySelector('.form__checkbox');
const checkboxes = document.querySelectorAll('.select__item');

// Проверка на checked чекбокса
checkboxForm.addEventListener('submit', function (event) {
    let massage = document.getElementById('massageCheckbox');
    let checkedCheckbox = false;
    event.preventDefault();

    for (item of checkboxes) {
        if (item.checked) {
            checkedCheckbox = true;
            this.submit();
        }
    }

    for (item of checkboxes) {
        item.checked = false;
    }

    if (massage === null && !checkedCheckbox) {
        document.querySelector('.select').insertAdjacentHTML('beforeend', `<div class="massage" id="massageCheckbox">Пожалуйста, выберите хотя бы одну пищевую добавку!</div>`);
    }
});

// Для кнопки "Выбрать всё"
const selectAllCheckboxes = document.getElementById('buttonAllCheckboxes');

selectAllCheckboxes.onclick = () => {
    for (item of checkboxes) {
        item.checked = true;
    }
}

// Добавление класса spoiler-is-active, когда спойлер открыт
const spoiler = document.querySelectorAll('.spoiler');

for(item of spoiler) {
    item.addEventListener('click', spoilerToggle);
}

function spoilerToggle() {
    this.classList.toggle('spoiler-is-active');
}

function spoilerDefault() {
    for(item of spoiler) {
        item.classList.remove('spoiler-is-active');
    }
}

// Информация про Резерв (Е800–Е899)
const selectsVertical = document.querySelectorAll('.select__vertical');

selectsVertical.item(7).insertAdjacentHTML('beforeend', `<div class="massage" id="massageReserve">Зарезервированные коды для веществ, пока не используются.</div>`);

//TEXTAREA
// Проверка textarea на наличие символов и повторов
const feedbackForm = document.querySelector('.feedback__form');
const feedbackTextArea = document.querySelector('.feedback__textarea');

feedbackTextArea.addEventListener('keydown', function (event) {
    if (event.repeat) {
        feedbackTextArea.value = '';
    }
});

feedbackForm.addEventListener('submit', function(event) {
    event.preventDefault();
    let massage = document.getElementById('massageTextArea');
    let testForm = false;

    if (feedbackTextArea.value.length > 50 && !(feedbackTextArea.value.charAt(0) === ' ')) {
        testForm = true;

        let toSent = confirm('Вы уверены, что хотите отправить данное сообщение?')
        if (toSent){
            this.submit();
            feedbackTextArea.value = '';
        }
    }

    if (massage === null && !testForm) {
        feedbackTextArea.insertAdjacentHTML('afterend', `<div class="massage" id="massageTextArea">Длина сообщения меньше 50 символов, или в начале его имеется пробел!</div>`);
    }
});