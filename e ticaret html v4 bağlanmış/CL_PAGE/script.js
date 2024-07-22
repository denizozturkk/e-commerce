document.addEventListener('DOMContentLoaded', function() {
    const token = localStorage.getItem('token');
    if (!token) {
        /* window.location.href = '../CL_LOGİN/login.html'; */
        console.log("Token yükleme sıkıntı")
        return;
    }
    
    // Diğer fonksiyonları buraya ekleyebilirsiniz
});

function showSection(sectionId) {
    document.querySelectorAll('main > section').forEach(section => {
        section.style.display = 'none';
    });
    document.getElementById(sectionId).style.display = 'block';
}

function addProduct(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const product = {
        id: formData.get('product-id'),
        name: formData.get('name'),
        category: formData.get('category'),
        quantity: parseInt(formData.get('quantity'), 10),
        cost: parseFloat(formData.get('cost')),
        supplier: formData.get('supplier')
    };
    inventory[product.id] = product;
    updateInventoryTable();
    event.target.reset();
}

function addOrder(event) {
    event.preventDefault();
    const formData = new FormData(event.target);
    const order = {
        id: formData.get('order-id'),
        productId: formData.get('product-id-order'),
        quantity: parseInt(formData.get('quantity-order'), 10),
        status: formData.get('status-order')
    };
    if (inventory[order.productId]) {
        if (order.status === 'incoming') {
            inventory[order.productId].quantity += order.quantity;
        } else if (order.status === 'outgoing') {
            if (inventory[order.productId].quantity >= order.quantity) {
                inventory[order.productId].quantity -= order.quantity;
            } else {
                alert('Insufficient quantity for the outgoing order.');
                return;
            }
        }
        updateInventoryTable();
        updateOrderTable(order);
    } else {
        alert('Product ID not found in inventory.');
    }
    event.target.reset();
}

function updateInventoryTable() {
    const tableBody = document.querySelector('#inventory-table tbody');
    if (!tableBody) {
        alert('Table body element not found');
        return;
    }
    tableBody.innerHTML = '';
    Object.values(inventory).forEach(product => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>${product.id}</td>
            <td>${product.name}</td>
            <td>${product.category}</td>
            <td>${product.quantity}</td>
            <td>${product.cost}</td>
            <td>${product.supplier}</td>
        `;
        tableBody.appendChild(row);
    });
}

function updateOrderTable(order) {
    const orderTable = document.querySelector('#order-table tbody');
    const exists = Array.from(orderTable.children).find(row => row.children[0].textContent === order.id);
    if (exists) {
        alert('Order with this ID already exists. Please use a unique Order ID.');
        return;
    }
    const out = Array.from(orderTable.children).find(row => row.children[4].textContent === "outgoing");

    if(out){
        const exceeds = Array.from(orderTable.children).find(row => row.children[3].textContent === order.quantity);
        if(exceeds){
            alert('Product stock exceeded, reselect the outgoing amount.');
        }
        if(Array.from(orderTable.children).find(row => row.children[3].textContent - order.quantity < 10)){

        }
    }
 
    const row = document.createElement('tr');
    row.innerHTML = `
        <td>${order.id}</td>
        <td>${order.productId}</td>
        <td>${order.quantity}</td>
        <td>${order.status}</td>
    `;
    orderTable.appendChild(row);
}

function showReportPage(reportType) {
    const reportText = prompt(`Please enter your ${reportType} report text:`);
    if (reportText !== null) {
        alert(`Your ${reportType} report:\n\n${reportText}`);
    }
}
