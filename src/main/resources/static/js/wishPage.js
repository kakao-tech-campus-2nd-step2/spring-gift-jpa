document.addEventListener('DOMContentLoaded', (event) => {
  const paginationElement = document.querySelector('.wishPagination');
  const pageNumber = Number(paginationElement.dataset.number);
  console.log(pageNumber);
  const pageSize = paginationElement.dataset.size;
  const totalElements = paginationElement.dataset.totalElements;
  const totalPages = paginationElement.dataset.totalPages;
  const hasNext = paginationElement.dataset.hasNext;
  const hasPrevious = paginationElement.dataset.hasPrevious;

  let startPage = Math.max(0, pageNumber - 2);
  let endPage = Math.min(startPage + 4, totalPages - 1);
  const pagesContainer = document.querySelector('.pages');

  let previousPage = document.getElementById('previousPage');
  let nextPage = document.getElementById('nextPage');
  let firstPage = document.getElementById('firstPage');
  let lastPage = document.getElementById('lastPage');

  if(pageNumber !== 0){
    firstPage.onclick = function (){
      getRequestWithToken('/api/products/wishes?page=0')
    }
    firstPage.style.display = 'flex';
  }

  if(pageNumber != totalPages - 1 && totalPages != 0){
    lastPage.onclick = function (){
      getRequestWithToken('/api/products/wishes?page=' + (totalPages - 1))
    }
    lastPage.style.display = 'flex';
  }

  if (hasPrevious === 'true') {
    console.log(hasPrevious);
    previousPage.onclick = function (){
      getRequestWithToken('/api/products/wishes?page=' + (pageNumber - 1))
    }
    previousPage.style.display = 'flex';
  }
  if (hasNext === 'true') {
    nextPage.onclick = function (){
      getRequestWithToken('/api/products/wishes?page=' + (pageNumber + 1))
    }
    nextPage.style.display = 'flex';
  }

  for (let count = startPage; count <= endPage; count++) {
    const pageLink = document.createElement('a');
    pageLink.textContent = count + 1;
    pageLink.className = 'page-' + count;
    pageLink.onclick = function (){
      getRequestWithToken('/api/products/wishes?page=' + count)
    }
    pagesContainer.appendChild(pageLink);

    if (count == pageNumber) {
      pageLink.style.fontWeight = 'bold';
      pageLink.style.backgroundColor = 'rgba(119,119,119,0.18)';
    }

  }
});
