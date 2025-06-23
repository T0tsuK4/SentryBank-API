const personType = document.getElementById("person-type");
const form = document.querySelector("form");

const toggleForm = () => {
  form.classList.toggle("juridica");

  const fisicas = form.querySelectorAll(".fis");
  const juridicas = form.querySelectorAll(".jur");

  if (form.classList.contains("juridica")) {
    fisicas.forEach((el) =>
      el.querySelector("input")?.removeAttribute("required")
    );
    juridicas.forEach((el) =>
      el.querySelector("input")?.setAttribute("required", true)
    );
  } else {
    juridicas.forEach((el) =>
      el.querySelector("input")?.removeAttribute("required")
    );
    fisicas.forEach((el) =>
      el.querySelector("input")?.setAttribute("required", true)
    );
  }
};

personType.addEventListener("change", toggleForm);

const aplicarMascaraCPF = (e) => {
  let cpf = e.target.value.replace(/\D/g, "").slice(0, 11);
  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d)/, "$1.$2");
  cpf = cpf.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
  e.target.value = cpf;
};

document.getElementsByName("cpf")[0]?.addEventListener("input", aplicarMascaraCPF);

const aplicarMascaraCNPJ = (e) => {
  let cnpj = e.target.value.replace(/\D/g, "").slice(0, 14);
  cnpj = cnpj.replace(/^(\d{2})(\d)/, "$1.$2");
  cnpj = cnpj.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
  cnpj = cnpj.replace(/\.(\d{3})(\d)/, ".$1/$2");
  cnpj = cnpj.replace(/(\d{4})(\d)/, "$1-$2");
  e.target.value = cnpj;
};

document.getElementsByName("cnpj")[0]?.addEventListener("input", aplicarMascaraCNPJ);

// Validação CPF
function validarCPF(cpf) {
  cpf = cpf.replace(/[^\d]+/g, "");
  if (cpf.length !== 11 || /^(\d)\1+$/.test(cpf)) return false;
  let soma = 0, resto;
  for (let i = 1; i <= 9; i++) soma += parseInt(cpf.substring(i - 1, i)) * (11 - i);
  resto = (soma * 10) % 11;
  if (resto === 10 || resto === 11) resto = 0;
  if (resto !== parseInt(cpf.substring(9, 10))) return false;
  soma = 0;
  for (let i = 1; i <= 10; i++) soma += parseInt(cpf.substring(i - 1, i)) * (12 - i);
  resto = (soma * 10) % 11;
  if (resto === 10 || resto === 11) resto = 0;
  return resto === parseInt(cpf.substring(10, 11));
}

// Validação CNPJ
function validarCNPJ(cnpj) {
  cnpj = cnpj.replace(/[^\d]+/g, "");
  if (cnpj.length !== 14) return false;
  if (/^(\d)\1+$/.test(cnpj)) return false;
  let tamanho = cnpj.length - 2;
  let numeros = cnpj.substring(0, tamanho);
  let digitos = cnpj.substring(tamanho);
  let soma = 0;
  let pos = tamanho - 7;
  for (let i = tamanho; i >= 1; i--) {
    soma += numeros.charAt(tamanho - i) * pos--;
    if (pos < 2) pos = 9;
  }
  let resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
  if (resultado !== parseInt(digitos.charAt(0))) return false;
  tamanho += 1;
  numeros = cnpj.substring(0, tamanho);
  soma = 0;
  pos = tamanho - 7;
  for (let i = tamanho; i >= 1; i--) {
    soma += numeros.charAt(tamanho - i) * pos--;
    if (pos < 2) pos = 9;
  }
  resultado = soma % 11 < 2 ? 0 : 11 - (soma % 11);
  return resultado === parseInt(digitos.charAt(1));
}

form.addEventListener("submit", async function (event) {
  event.preventDefault();

  const tipoPessoa = personType.value;

  const nome =
    tipoPessoa === "fisica"
      ? document.getElementsByName("nome")[0].value
      : document.getElementsByName("nome-fantasia")[0].value;
  const email = document.getElementsByName("email")[0].value;
  const senha = document.getElementsByName("senha")[0].value;
  const cpf = document.getElementsByName("cpf")[0].value;
  const cnpj = document.getElementsByName("cnpj")[0].value;
  const dataNascimento = document.getElementsByName("data-nascimento")[0].value;
  const dataCriacao = document.getElementsByName("data-criacao")[0].value;

  if (tipoPessoa === "fisica" && !validarCPF(cpf)) {
    alert("CPF inválido.");
    return;
  }
  if (tipoPessoa === "juridica" && !validarCNPJ(cnpj)) {
    alert("CNPJ inválido.");
    return;
  }

  let url = "";
  let body = {};

  if (tipoPessoa === "fisica") {
    url = "http://localhost:8080/api/clientes-fisicos";
    body = {
      nome: nome,
      email: email,
      senha: senha,
      cpf: cpf,
      dataNascimento: dataNascimento
    };
  } else {
    url = "http://localhost:8080/api/clientes-juridicos";
    body = {
      nome: nome,
      email: email,
      senha: senha,
      cnpj: cnpj,
      nomeFantasia: nome,
      dataCriacao: dataCriacao
    };
  }

  try {
    const response = await fetch(url, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(body)
    });

    if (response.ok) {
      alert(
        tipoPessoa === "fisica"
          ? "Cliente físico cadastrado com sucesso!"
          : "Cliente jurídico cadastrado com sucesso!"
      );
      form.reset();
    } else {
      const erro = await response.text();
      alert("Erro ao cadastrar: " + erro);
    }
  } catch (error) {
    console.error("Erro:", error);
    alert("Erro ao conectar com a API.");
  }
});
