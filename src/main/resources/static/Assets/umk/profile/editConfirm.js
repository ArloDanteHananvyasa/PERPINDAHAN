// Mengambil elemen-elemen dari HTML
const submitButton = document.querySelector(".submit-button"); // Tombol 'Buat Akun'
const overlay = document.getElementById("regis-sent"); // Elemen overlay
const noButton = document.querySelector(".no-btn"); // Tombol 'OK' di overlay
const yesButton = document.querySelector(".yes-btn"); // Tombol 'OK' di overlay
const form = document.querySelector(".edit-form");

// Menampilkan overlay ketika tombol 'Buat Akun' diklik
submitButton.addEventListener("click", function () {
  overlay.classList.remove("hidden");

  document.querySelector(".container").classList.add("blurred"); // Menghapus class 'hidden' untuk menampilkan overlay
});

// Menyembunyikan overlay ketika tombol 'OK' diklik
noButton.addEventListener("click", function () {
  overlay.classList.add("hidden");
  document.querySelector(".container").classList.remove("blurred"); // Menambahkan class 'hidden' untuk menyembunyikan overlay
});

// Menyembunyikan overlay ketika tombol 'OK' diklik
yesButton.addEventListener("click", function () {
  overlay.classList.add("hidden");
  document.querySelector(".container").classList.remove("blurred"); // Menambahkan class 'hidden' untuk menyembunyikan overlay
  form.submit();
});


