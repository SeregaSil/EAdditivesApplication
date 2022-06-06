const buttonFile = document.getElementById('buttonFile');
const buttonFileName = document.querySelector('.button__file__name');
const text = 'Добавить файл';

buttonFile.addEventListener("change", function () {
    if (this.files.length === 1) {
        buttonFileName.innerHTML = this.files[0].name;
    } else buttonFileName.innerHTML = text;
})

//BUTTON SUBMIT FILE
const buttonFileSubmit = document.querySelector('.form__file');

buttonFileSubmit.addEventListener('submit', function(event) {
    event.preventDefault();
    let massage = document.getElementById('massageFile');
    let sentFile = false;
    let File = document.getElementById('buttonFile');

    if (File.files.length === 1) {
        sentFile = true;
        this.submit();
        File.remove();
    }

    if (massage === null && !sentFile) {
        buttonFileSubmit.insertAdjacentHTML('beforeend', `<div class="massage" id="massageFile">Добавьте файл.</div>`);
    }
});