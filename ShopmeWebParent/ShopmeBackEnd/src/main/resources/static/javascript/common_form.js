// Chức năng Upload Image
$(document).ready(function() {
	$("#fileAction").on("click", function() {
		$('#fileImage').change(function(e) {
			fileSize = this.files[0].size;
			alert("File size: " + fileSize);

			if (fileSize > 1048576) {
				this.setCustomValidity("You must choose image less than 1MB!");
				this.reportValidity();
			} else {
				this.setCustomValidity("");
				showImageThumbnail(this);
			}
		})
	})
})

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
			window.location = "[[@{/users}]]";
		});
	});
});		