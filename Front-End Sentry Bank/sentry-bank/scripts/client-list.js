document.addEventListener('DOMContentLoaded', () => {
  const tableContainer = document.querySelector('.db__table');

  const criarTabela = (clientes, tipo) => {
    const table = document.createElement('table');
    table.classList.add('client-table');

    const thead = document.createElement('thead');
    const headerRow = document.createElement('tr');

    const headers = ['ID', 'Nome', 'Email', 'Tipo'];
    headers.forEach((header) => {
      const th = document.createElement('th');
      th.innerText = header;
      headerRow.appendChild(th);
    });

    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement('tbody');

    clientes.forEach((cliente) => {
      const row = document.createElement('tr');

      const idCell = document.createElement('td');
      idCell.innerText = cliente.id;
      row.appendChild(idCell);

      const nomeCell = document.createElement('td');
      nomeCell.innerText = cliente.nome;
      row.appendChild(nomeCell);

      const emailCell = document.createElement('td');
      emailCell.innerText = cliente.email;
      row.appendChild(emailCell);

      const tipoCell = document.createElement('td');
      tipoCell.innerText = tipo;
      row.appendChild(tipoCell);

      tbody.appendChild(row);
    });

    table.appendChild(tbody);
    tableContainer.appendChild(table);
  };

  const listarClientesFisicos = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/clientes-fisicos');
      if (!response.ok) {
        throw new Error('Erro ao buscar clientes físicos');
      }
      const clientesFisicos = await response.json();
      criarTabela(clientesFisicos, 'Físico');
    } catch (error) {
      console.error('Erro ao buscar clientes físicos:', error);
    }
  };

  const listarClientesJuridicos = async () => {
    try {
      const response = await fetch('http://localhost:8080/api/clientes-juridicos');
      if (!response.ok) {
        throw new Error('Erro ao buscar clientes jurídicos');
      }
      const clientesJuridicos = await response.json();
      criarTabela(clientesJuridicos, 'Jurídico');
    } catch (error) {
      console.error('Erro ao buscar clientes jurídicos:', error);
    }
  };

  listarClientesFisicos();
  listarClientesJuridicos();
});
