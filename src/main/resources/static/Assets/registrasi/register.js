// Mengambil elemen-elemen dari HTML
const submitButton = document.querySelector('.submit-button'); // Tombol 'Buat Akun'
const overlay = document.getElementById('regis-sent'); // Elemen overlay
const okButton = document.querySelector('.ok-btn'); // Tombol 'OK' di overlay
const form = document.querySelector('.register-form');

// Menampilkan overlay ketika tombol 'Buat Akun' diklik
submitButton.addEventListener('click', function () {
    overlay.classList.remove('hidden');
    document.querySelector(".container").classList.add('blurred'); // Menghapus class 'hidden' untuk menampilkan overlay
});


// Menyembunyikan overlay ketika tombol 'OK' diklik
okButton.addEventListener('click', function () {
    overlay.classList.add('hidden');
    document.querySelector(".container").classList.remove('blurred'); // Menambahkan class 'hidden' untuk menyembunyikan overlay
    form.submit(); // Submit the form manually
});
