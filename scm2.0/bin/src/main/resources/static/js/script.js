const modeBtn = document.querySelector("#theme_change_button");
const body = document.body;
const html = document.documentElement;
let currentMode = localStorage.getItem("theme") || "light";

// Function to apply theme
const applyTheme = (mode) => {
    if (mode === "dark") {
        body.style.backgroundColor = "#111827"; // Tailwind gray-900
        body.style.color = "white";
        html.classList.add("dark");
    } else {
        body.style.backgroundColor = "white";
        body.style.color = "black";
        html.classList.remove("dark");
    }

    localStorage.setItem("theme", mode);
    modeBtn.querySelector("span").textContent = mode === "light" ? "Dark" : "Light";
};

// Apply theme on page load
applyTheme(currentMode);

// Toggle theme on button click
modeBtn.addEventListener("click", () => {
    currentMode = currentMode === "light" ? "dark" : "light";
    applyTheme(currentMode);
});


