$(document).ready(function () {


    $('#accedi').click(function () {
        var email = $('#email')[0].value;
        var password = $('#password')[0].value;
        $.ajax({
            method: "POST",
            data: JSON.stringify({
                "email": email,
                "password": password
            }),
            url: "http://localhost:8080/session/login",
            headers: {
                "Content-Type":"application/json"
            },
            success: function (risposta) {
                checkLogin(risposta);
            },
            error: function () {
                $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function(){$(".btn-close")[0].click()},4000);
            }
        });
    });

    $('#logout').click(function () {
        sessionStorage.removeItem('token');
        sessionStorage.removeItem('privileges');
        setTimeout(function(){window.location.href = "login.html";},1000);
    });

    $('#checkCredito').click(function () {
        var numeroCarta = $('#numeroCarta')[0].value;
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/card/"+numeroCarta,
            success: function (risposta) {
                comunicaCredito(risposta);
            },
            error: function (stato) {
                comunicaErroreCredito(stato.status);
            }
        });
    });

});



function checkLogin(risposta) {
    if(risposta.error){
        //alert(risposta.errorMessage);
        $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i> "+risposta.errorMessage+"<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
        setTimeout(function(){$(".btn-close")[0].click()},2000);
    }else{
        sessionStorage.setItem('token', risposta.session.token);
        sessionStorage.setItem('privileges', risposta.session.privileges);
        setTimeout(function(){window.location.href = "index.html";},1000);
    }
}

function comunicaCredito(risposta) {
    $('#bodyModale').empty();
    $('#bodyModale').append('<p>Il credito residuo della carta &egrave;: '+risposta.card.credit+'&euro;</p>');
    
}

function comunicaErroreCredito(status){
    $('#bodyModale').empty();
    if(status==404){
        $('#bodyModale').append('Carta non trovata');
        return;
    }
    $('#bodyModale').append('Si è verificato un errore si prega di riprovare.');
}