document.addEventListener('DOMContentLoaded', function () {
    let checkboxes = document.querySelectorAll('input[type="checkbox"]');
    let compareButton = document.getElementById('compareButton');
    let selectedCounter = 0;

    checkboxes.forEach(function (checkbox) {
        checkbox.addEventListener('change', function () {
            if (this.checked) {
                selectedCounter++;
            } else {
                selectedCounter--;
            }
        });
    });

    compareButton.addEventListener('click', function (event) {
        if (selectedCounter !== 2) {
            event.preventDefault();
            alert('Please select exactly 2 cars for comparison.');
        } else {
            let selectedPositions = document.querySelectorAll('input[type="checkbox"]:checked');
            let position1 = selectedPositions[0].value;
            let position2 = selectedPositions[1].value;

            document.getElementById('position1Input').value = position1;
            document.getElementById('position2Input').value = position2;

            document.getElementById('compareForm').submit();
        }
    });
});