document.addEventListener("DOMContentLoaded", () => {
  if (window.lucide) {
    window.lucide.createIcons();
  }

  initThemeToggle();
  initMobileNav();
  initRevealAnimations();
  initHeroParticles();
  initCertificateLightbox();
  initContactAjaxForm();
  initTopProgressBar();
});

function initTopProgressBar() {
  const bar = document.getElementById("pageProgressBar");
  if (!bar) return;
  bar.style.width = "65%";
  window.addEventListener("load", () => {
    bar.style.width = "100%";
    setTimeout(() => {
      bar.style.opacity = "0";
    }, 220);
  });
}

function initThemeToggle() {
  const html = document.documentElement;
  const toggleBtn = document.getElementById("themeToggleBtn");
  const saved = localStorage.getItem("portfolio-theme") || "dark";
  applyTheme(saved);

  if (toggleBtn) {
    toggleBtn.addEventListener("click", () => {
      const next = html.getAttribute("data-theme") === "light" ? "dark" : "light";
      applyTheme(next);
      localStorage.setItem("portfolio-theme", next);
    });
  }

  function applyTheme(theme) {
    html.setAttribute("data-theme", theme);
    if (!toggleBtn) return;
    const sunIcon = toggleBtn.querySelector("[data-theme-icon='sun']");
    const moonIcon = toggleBtn.querySelector("[data-theme-icon='moon']");
    if (sunIcon && moonIcon) {
      sunIcon.style.display = theme === "dark" ? "block" : "none";
      moonIcon.style.display = theme === "light" ? "block" : "none";
    }
  }
}

function initMobileNav() {
  const btn = document.getElementById("mobileMenuBtn");
  const drawer = document.getElementById("mobileDrawer");
  if (!btn || !drawer) return;

  btn.addEventListener("click", () => {
    const isOpen = drawer.classList.toggle("open");
    btn.setAttribute("aria-expanded", String(isOpen));
  });

  drawer.querySelectorAll("a").forEach((link) => {
    link.addEventListener("click", () => {
      drawer.classList.remove("open");
      btn.setAttribute("aria-expanded", "false");
    });
  });
}

function initRevealAnimations() {
  const elements = document.querySelectorAll(".reveal");
  if (!("IntersectionObserver" in window)) {
    elements.forEach((el) => el.classList.add("visible"));
    return;
  }

  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add("visible");
          observer.unobserve(entry.target);
        }
      });
    },
    { threshold: 0.1 }
  );

  elements.forEach((el) => observer.observe(el));
}

function initHeroParticles() {
  const canvas = document.getElementById("heroCanvas");
  if (!canvas) return;
  const ctx = canvas.getContext("2d");
  let width = 0;
  let height = 0;
  let particles = [];

  const resize = () => {
    width = canvas.width = canvas.offsetWidth;
    height = canvas.height = canvas.offsetHeight;
    const count = Math.min(36, Math.max(14, Math.floor(width / 38)));
    particles = Array.from({ length: count }, () => ({
      x: Math.random() * width,
      y: Math.random() * height,
      vx: (Math.random() - 0.5) * 0.45,
      vy: (Math.random() - 0.5) * 0.45,
      r: Math.random() * 1.8 + 1,
    }));
  };

  const render = () => {
    ctx.clearRect(0, 0, width, height);
    for (let i = 0; i < particles.length; i++) {
      const p = particles[i];
      p.x += p.vx;
      p.y += p.vy;
      if (p.x < 0 || p.x > width) p.vx *= -1;
      if (p.y < 0 || p.y > height) p.vy *= -1;

      ctx.beginPath();
      ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2);
      ctx.fillStyle = "rgba(129, 140, 248, 0.55)";
      ctx.fill();

      for (let j = i + 1; j < particles.length; j++) {
        const q = particles[j];
        const dx = p.x - q.x;
        const dy = p.y - q.y;
        const dist = Math.hypot(dx, dy);
        if (dist < 130) {
          ctx.beginPath();
          ctx.moveTo(p.x, p.y);
          ctx.lineTo(q.x, q.y);
          ctx.strokeStyle = `rgba(99, 102, 241, ${(1 - dist / 130) * 0.22})`;
          ctx.lineWidth = 1;
          ctx.stroke();
        }
      }
    }
    requestAnimationFrame(render);
  };

  resize();
  window.addEventListener("resize", resize);
  render();
}

function initCertificateLightbox() {
  const modal = document.getElementById("certLightboxModal");
  if (!modal) return;

  const titleEl = document.getElementById("lightboxCertTitle");
  const issuerEl = document.getElementById("lightboxCertIssuer");
  const imgEl = document.getElementById("lightboxCertImage");
  const pdfLink = document.getElementById("lightboxCertPdfLink");
  const detailLink = document.getElementById("lightboxCertDetailLink");
  const closeBtn = document.getElementById("lightboxCloseBtn");

  document.querySelectorAll("[data-cert-trigger]").forEach((trigger) => {
    trigger.addEventListener("click", () => {
      const title = trigger.getAttribute("data-cert-title") || "Certificate";
      const issuer = trigger.getAttribute("data-cert-issuer") || "";
      const image = trigger.getAttribute("data-cert-image") || "";
      const pdf = trigger.getAttribute("data-cert-pdf") || "";
      const detailUrl = trigger.getAttribute("data-cert-detail") || "#";

      if (titleEl) titleEl.textContent = title;
      if (issuerEl) issuerEl.textContent = issuer;
      if (imgEl) {
        imgEl.src = image;
        imgEl.alt = title;
      }
      if (pdfLink) {
        if (pdf) {
          pdfLink.href = pdf;
          pdfLink.style.display = "inline-flex";
        } else {
          pdfLink.style.display = "none";
        }
      }
      if (detailLink) {
        detailLink.href = detailUrl;
      }
      modal.classList.add("open");
      document.body.style.overflow = "hidden";
    });
  });

  const closeModal = () => {
    modal.classList.remove("open");
    document.body.style.overflow = "";
  };

  if (closeBtn) closeBtn.addEventListener("click", closeModal);
  modal.addEventListener("click", (e) => {
    if (e.target === modal) closeModal();
  });
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape" && modal.classList.contains("open")) {
      closeModal();
    }
  });
}

function initContactAjaxForm() {
  const form = document.getElementById("portfolioContactForm");
  if (!form) return;

  const statusBox = document.getElementById("contactAjaxStatus");
  const submitBtn = document.getElementById("contactSubmitBtn");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    clearFieldErrors();
    if (statusBox) statusBox.innerHTML = "";

    const payload = {
      name: form.querySelector("[name='name']").value.trim(),
      email: form.querySelector("[name='email']").value.trim(),
      subject: form.querySelector("[name='subject']").value.trim(),
      message: form.querySelector("[name='message']").value.trim(),
    };

    const csrfToken = form.querySelector("[name='_csrf']")?.value;
    const originalBtnHtml = submitBtn ? submitBtn.innerHTML : "";
    if (submitBtn) {
      submitBtn.disabled = true;
      submitBtn.innerHTML = "Sending...";
    }

    try {
      const headers = {
        "Content-Type": "application/json",
        "Accept": "application/json",
      };
      if (csrfToken) {
        headers["X-CSRF-TOKEN"] = csrfToken;
      }

      const res = await fetch("/api/contact", {
        method: "POST",
        headers,
        body: JSON.stringify(payload),
      });

      const data = await res.json();
      if (res.ok && data.success) {
        if (statusBox) {
          statusBox.innerHTML = `<div class="alert-banner alert-success-custom">${escapeHtml(data.message)}</div>`;
        }
        form.reset();
      } else {
        if (data.errors) {
          Object.entries(data.errors).forEach(([field, msg]) => {
            const errEl = document.getElementById(`error-${field}`);
            if (errEl) errEl.textContent = msg;
          });
        }
        if (statusBox) {
          statusBox.innerHTML = `<div class="alert-banner alert-error-custom">${escapeHtml(data.message || "Please check your inputs.")}</div>`;
        }
      }
    } catch (err) {
      if (statusBox) {
        statusBox.innerHTML = `<div class="alert-banner alert-error-custom">Network error while sending message. Please try again.</div>`;
      }
    } finally {
      if (submitBtn) {
        submitBtn.disabled = false;
        submitBtn.innerHTML = originalBtnHtml;
      }
    }
  });

  function clearFieldErrors() {
    form.querySelectorAll(".field-error").forEach((el) => (el.textContent = ""));
  }

  function escapeHtml(str) {
    const div = document.createElement("div");
    div.textContent = str;
    return div.innerHTML;
  }
}
