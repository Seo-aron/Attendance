// 탭 메뉴중 선택한 메뉴에 css효과 주기
document.addEventListener("DOMContentLoaded", function() {
    const currentPath = window.location.pathname;
    const navLinks = document.querySelectorAll("nav a");

    navLinks.forEach(link => {
        const linkPath = new URL(link.href).pathname;

        if (linkPath === currentPath) {
            link.classList.add("active");
        }
    });

    // logout
    const logoutBtn = document.getElementById("simple-logout-btn");

    if (logoutBtn) {
        logoutBtn.addEventListener("click", () => {
            window.location.href = '/login/login';
        });
    }
});