document.addEventListener('DOMContentLoaded', () => {
  const form = document.getElementById('deleteForm');
  const idInput = document.getElementById('id');
  const tipoInput = document.getElementById('tipo');

  form.addEventListener('submit', async (event) => {
    event.preventDefault();

    const id = idInput.value.trim();
    const tipo = tipoInput.value;

    if (!id || !tipo) {
      alert('Por favor, preencha o ID e selecione o tipo de cliente.');
      return;
    }

    let baseUrl = '';
    let urlBusca = '';
    let urlDelete = '';

    if (tipo === 'fisico') {
      baseUrl = 'http://localhost:8080/api/clientes-fisicos';
      urlBusca = `${baseUrl}/fisico/${id}`;
      urlDelete = `${baseUrl}/fisico/${id}`;
    } else if (tipo === 'juridico') {
      baseUrl = 'http://localhost:8080/api/clientes-juridicos';
      urlBusca = `${baseUrl}/juridico/${id}`;
      urlDelete = `${baseUrl}/juridico/${id}`;
    } else {
      alert('Tipo de cliente inválido.');
      return;
    }

    try {
      const buscaResponse = await fetch(urlBusca);
      if (buscaResponse.status === 404) {
        alert(`Cliente ${tipo} com ID ${id} não encontrado.`);
        return;
      }
      if (!buscaResponse.ok) {
        alert('Erro ao verificar cliente. Tente novamente.');
        return;
      }

      const confirmacao = confirm(
        `Deseja realmente deletar o cliente ${tipo} com ID ${id}?`
      );
      if (!confirmacao) {
        return;
      }

      const deleteResponse = await fetch(urlDelete, {
        method: 'DELETE',
      });

      if (deleteResponse.status === 204) {
        alert(`Cliente ${tipo} com ID ${id} deletado com sucesso!`);
        form.reset();
      } else {
        alert('Erro ao deletar cliente. Tente novamente.');
      }
    } catch (error) {
      console.error('Erro na requisição:', error);
      alert('Erro na conexão com o servidor.');
    }
  });
});
