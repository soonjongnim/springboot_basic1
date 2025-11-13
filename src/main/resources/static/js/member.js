document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");
    form.addEventListener("submit", (e) => {
        const name = form.username.value.trim();
        if (!name) {
            e.preventDefault();
            alert("이름을 입력하세요!");
        }
    });
});
