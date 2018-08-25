$(document).ready(function () {
    $('#cssTable').DataTable();
    $('.dataTables_length').addClass('bs-select');
});

function gridPictures() {
    var elements = document.getElementsByClassName("column-custom-grid");
    var i;

    for (i = 0; i < elements.length; i++) {
        elements[i].style.flex = "25%";
    }
}

function checkFileSize(field) {
    var fileSize = field.files[0].size;
    var fileName = field.files[0].name;
    var fileExtension = fileName.substring(fileName.lastIndexOf('.'), fileName.size);

    if (fileSize > 4000000 || fileExtension !== '.JPG' || fileExtension !== '.JPEG'
        || fileExtension !== '.jpg'
        || fileExtension !== '.jpeg') {

        document.getElementById('invalidFileSizeMessage').removeAttribute('hidden');
        field.value = "";
        setTimeout(function () {
            document.getElementById('invalidFileSizeMessage').setAttribute('hidden', '');
        }, 10000)
    }
}
