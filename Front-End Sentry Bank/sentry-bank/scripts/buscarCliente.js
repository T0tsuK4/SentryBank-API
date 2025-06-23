document.addEventListener('DOMContentLoaded', () => {
  const tipoCliente = document.getElementById('tipoCliente');
  const idInput = document.getElementById('id');
  const btnBuscar = document.getElementById('btnBuscar');
  const form = document.getElementById('clienteForm');

  const nomeInput = document.getElementById('nome');
  const emailInput = document.getElementById('email');
  const senhaInput = document.getElementById('senha');

  const cpfInput = document.getElementById('cpf');
  const dataNascimentoInput = document.getElementById('dataNascimento');

  const cnpjInput = document.getElementById('cnpj');
  const nomeFantasiaInput = document.getElementById('nomeFantasia');
  const dataCriacaoInput = document.getElementById('dataCriacao');

  const fisicoFields = document.querySelector('.fisicoFields');
  const juridicoFields = document.querySelector('.juridicoFields');
  const formCampos = document.getElementById('formCampos');

  tipoCliente.addEventListener('change', () => {
    const tipo = tipoCliente.value;

    if (tipo === 'fisico') {
      formCampos.classList.remove('hidden');
      fisicoFields.classList.remove('hidden');
      juridicoFields.classList.add('hidden');
      limparCamposJuridico();
    } else if (tipo === 'juridico') {
      formCampos.classList.remove('hidden');
      juridicoFields.classList.remove('hidden');
      fisicoFields.classList.add('hidden');
      limparCamposFisico();
    } else {
      formCampos.classList.add('hidden');
      fisicoFields.classList.add('hidden');
      juridicoFields.classList.add('hidden');
    }

    limparCamposComuns();
  });

  btnBuscar.addEventListener('click', async (e) => {
    e.preventDefault();

    const tipo = tipoCliente.value;
    const id = idInput.value;

    if (!tipo) {
      alert('Selecione o tipo de cliente.');
      return;
    }

    if (!id) {
      alert('Informe o ID do cliente.');
      return;
    }

    let url = '';
    if (tipo === 'fisico') {
      url = `http://localhost:8080/api/clientes-fisicos/fisico/${id}`;
    } else if (tipo === 'juridico') {
      url = `http://localhost:8080/api/clientes-juridicos/juridico/${id}`;
    }

    try {
      const response = await fetch(url);
      if (!response.ok) throw new Error('Cliente não encontrado.');

      const data = await response.json();

      nomeInput.value = data.nome || '';
      emailInput.value = data.email || '';
      senhaInput.value = data.senha || ''; 

      if (tipo === 'fisico') {
        cpfInput.value = data.cpf || '';
        dataNascimentoInput.value = data.dataNascimento || '';
      } else if (tipo === 'juridico') {
        cnpjInput.value = data.cnpj || '';
        nomeFantasiaInput.value = data.nomeFantasia || '';
        dataCriacaoInput.value = data.dataCriacao || '';
      }
    } catch (error) {
      alert('Erro: ' + error.message);
      console.error(error);
    }
  });

  form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const tipo = tipoCliente.value;
    const id = idInput.value;

    if (!tipo || !id) {
      alert('Selecione o tipo e informe o ID do cliente.');
      return;
    }

    let url = '';
    let payload = {
      nome: nomeInput.value,
      email: emailInput.value,
      senha: senhaInput.value
    };

    if (tipo === 'fisico') {
      url = `http://localhost:8080/api/clientes-fisicos/fisico/${id}`;
      payload = {
        ...payload,
        cpf: cpfInput.value,
        dataNascimento: dataNascimentoInput.value
      };
    } else if (tipo === 'juridico') {
      url = `http://localhost:8080/api/clientes-juridicos/juridico/${id}`;
      payload = {
        ...payload,
        cnpj: cnpjInput.value,
        nomeFantasia: nomeFantasiaInput.value,
        dataCriacao: dataCriacaoInput.value
      };
    }

    try {
      const response = await fetch(url, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      });

      if (response.ok) {
        alert('Cliente atualizado com sucesso!');
      } else {
        const errorData = await response.json();
        alert('Erro ao atualizar: ' + (errorData.message || response.status));
      }
    } catch (error) {
      alert('Erro: ' + error.message);
      console.error(error);
    }
  });

  function limparCamposComuns() {
    nomeInput.value = '';
    emailInput.value = '';
    senhaInput.value = '';
    idInput.value = '';
  }

  function limparCamposFisico() {
    cpfInput.value = '';
    dataNascimentoInput.value = '';
  }

  function limparCamposJuridico() {
    cnpjInput.value = '';
    nomeFantasiaInput.value = '';
    dataCriacaoInput.value = '';
  }
});
