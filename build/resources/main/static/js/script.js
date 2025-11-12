document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");
    form.addEventListener("submit", (e) => {
        const username = form.username.value.trim();
        if (!username) {
            e.preventDefault();
            alert("아이디를 입력하세요!");
        }
    });
});
