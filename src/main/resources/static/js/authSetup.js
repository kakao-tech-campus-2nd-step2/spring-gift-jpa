const authSetup = {

    setAuthorizationHeader: function (){
        $.ajaxSetup({
            beforeSend: function(xhr) {
                let token = localStorage.getItem('token');
                if (token) {
                    xhr.setRequestHeader('Authorization', 'Bearer ' + token);
                }
            }
        });
    },
    storeToken: function (token){
        localStorage.setItem('token', token);
    }
}