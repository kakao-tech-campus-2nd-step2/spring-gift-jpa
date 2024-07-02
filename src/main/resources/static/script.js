$(document).ready(function() {
  $('.deleteBtn').click(function() {
    const productId = $(this).data('id');
    $.ajax({
      url: `/admin/products/${productId}`,
      type: 'DELETE',
      success: function(response) {
        console.log('DELETE 요청이 성공적으로 보내졌습니다.');
        console.log(response);
      },
      error: function(error) {
        console.error('DELETE 요청 중 오류가 발생했습니다.');
        console.error(error);
      }
    });
  });
});
