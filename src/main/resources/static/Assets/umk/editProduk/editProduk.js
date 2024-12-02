document.addEventListener("DOMContentLoaded", function () {
  // Tombol Submit dan Overlay
  const submitButton = document.querySelector(".submit-button");
  const overlay = document.getElementById("regis-sent");
  const noButton = document.querySelector(".no-btn");
  const yesButton = document.querySelector(".yes-btn");
  const form = document.getElementById("input-form");

  // Fungsi Validasi
  function validateForm(event) {
    const harga = document.getElementById("harga").value;
    const stok = document.getElementById("stok").value;

    if (harga <= 0 || stok <= 0) {
      alert("Harga dan jumlah stok harus lebih dari 0.");
      event.preventDefault(); // Mencegah pengiriman jika tidak valid
      return false; // Mengembalikan nilai false jika tidak valid
    }
    return true; // Mengembalikan nilai true jika valid
  }

  // Tombol "Tambah Produk" Menampilkan Overlay
  if (submitButton && overlay) {
    submitButton.addEventListener("click", function () {
      overlay.classList.remove("hidden");
      document.querySelector(".main-content").classList.add("blurred");
    });
  }

  // Tombol "Tidak" Menyembunyikan Overlay
  if (noButton) {
    noButton.addEventListener("click", function () {
      overlay.classList.add("hidden");
      document.querySelector(".main-content").classList.remove("blurred");
    });
  }

  // Tombol "Ya" Menyimpan Data Jika Valid
  if (yesButton) {
    yesButton.addEventListener("click", function () {
      if (validateForm(new Event("submit"))) {
        alert("Produk berhasil diubah!");
        overlay.classList.add("hidden");
        document.querySelector(".main-content").classList.remove("blurred");
        // Di sini, tambahkan logika untuk benar-benar mengirim data jika diperlukan
      }
    });
  }
});
