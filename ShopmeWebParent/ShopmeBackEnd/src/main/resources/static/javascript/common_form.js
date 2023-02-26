// Chức năng Upload Image
$(document).ready(function() {
	$("#fileAction").on("click", function() {
		$('#fileImage').change(function(e) {
			if (!checkFileSize(this)) {
				return;
			}

			showImageThumbnail(this);
		})
	})
})

function checkFileSize(fileInput) {
	fileSize = fileInput.files[0].size;

	if (fileSize > MAX_FILE_SIZE) {
		fileInput.setCustomValidity("You must choose an image less than " + MAX_FILE_SIZE + " bytes!");
		fileInput.reportValidity();

		return false;
	} else {
		fileInput.setCustomValidity("");

		return true;
	}
}

function showImageThumbnail(fileInput) {
	var file = fileInput.files[0];
	var reader = new FileReader();
	reader.onload = function(e) {
		$("#fileAction").on("click", function() {
			$("#thumbnail").attr("src", e.target.result);
		})
	}
	reader.readAsDataURL(file);
}


// Chức năng Button Cancel quay trở về trang User
$(document).ready(function() {
	$("#buttonCancel").on("click", function() {
		$("#cancelAction").click(function() {
			window.location = "[[@{/moduleURL}]]";
		});
	});
});

// Modal 
function showModalDialog(title, message) {
	$("#modalTitle").text(title);
	$("#modalBody").text(message);
	$("#modalDialog").modal();
}

function showErrorModal(message) {
	showModalDialog("Error", message);
}

function showWarningModal(message) {
	showModalDialog("Warning", message);
}