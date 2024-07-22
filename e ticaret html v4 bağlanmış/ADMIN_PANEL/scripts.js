document.addEventListener('DOMContentLoaded', function() {
  loadDashboardData();
  loadProducts();
});

function loadDashboardData() {
  const token = localStorage.getItem('adminToken');

  fetch('http://localhost:8080/api/v1/product/getProducts', {
      headers: {
          'Authorization': 'Bearer ' + token
      }
  })
      .then(response => response.json())
      .then(products => {
          document.getElementById('product-count').innerText = products.length;
      })
      .catch(error => console.error('Error loading products:', error));
}

function loadProducts() {
  const token = localStorage.getItem('adminToken');
  const tableBody = document.getElementById('product-table-body');
  if (tableBody) {
      fetch('http://localhost:8080/api/v1/product/getProducts', {
          headers: {
              'Authorization': 'Bearer ' + token
          }
      })
          .then(response => response.json())
          .then(products => {
              tableBody.innerHTML = '';
              products.forEach(product => {
                  const row = document.createElement('tr');
                  row.innerHTML = `
                      <td>${product.name}</td>
                      <td>${product.description}</td>
                      <td>${product.price}</td>
                      <td>${product.quantity}</td>
                      <td>
                          <a href="Edit.html?id=${product.id}" style="color: chartreuse;">Edit</a>
                          <a href="#" onclick="deleteProduct(${product.id})" style="color: red;">Delete</a>
                      </td>
                  `;
                  tableBody.appendChild(row);
              });
          })
          .catch(error => console.error('Error loading products:', error));
  }
}

function deleteProduct(productId) {
  const token = localStorage.getItem('adminToken');

  fetch(`http://localhost:8080/api/v1/product/deleteProduct/${productId}`, {
      method: 'DELETE',
      headers: {
          'Authorization': 'Bearer ' + token
      }
  })
  .then(() => {
      loadProducts();
  })
  .catch(error => console.error('Error deleting product:', error));
}

document.getElementById('add-product-form')?.addEventListener('submit', function(event) {
  event.preventDefault();

  const token = localStorage.getItem('adminToken');
  const adminId = localStorage.getItem('adminId');  // Admin ID'yi alıyoruz
  const formData = new FormData();
  formData.append('product', new Blob([JSON.stringify({
      name: document.getElementById('product-name').value,
      description: document.getElementById('product-description').value,
      price: document.getElementById('product-price').value,
      quantity: document.getElementById('product-quantity').value
  })], { type: "application/json" }));
  formData.append('image', document.getElementById('product-image').files[0]);

  const numOfProduct = document.getElementById('product-quantity').value;  // Ürün sayısını alıyoruz

  fetch(`http://localhost:8080/api/v1/admin/addProduct/${adminId}/numOfProduct/${numOfProduct}`, {
      method: 'POST',
      headers: {
          'Authorization': 'Bearer ' + token
      },
      body: formData
  })
  .then(response => {
      if (!response.ok) {
          throw new Error('Network response was not ok');
      }
      return response.json();
  })
  .then(data => {
      /* window.location.href = 'P_index.html'; */
  })
  .catch(error => console.error('Error adding product:', error));
});
