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
                "Content-Type": "application/json"
            },
            success: function (risposta) {
                checkLogin(risposta);
            },
            error: function () {
                $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function () { $(".btn-close")[0].click() }, 4000);
            }
        });
    });

    $('#logout').click(function () {
        $.ajax({
            method: "POST",
            url: "http://localhost:8080/session/logout",
            headers: {
                "SESSION-TOKEN": sessionStorage.getItem('token')
            },
            success: function (risposta) {
                sessionStorage.removeItem('token');
                sessionStorage.removeItem('privileges');
                setTimeout(function () { window.location.href = "login.html"; }, 1000);
            },
            error: function (stato) {
                $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function () { $(".btn-close")[0].click() }, 4000);
            }
        });
    });

    $('#checkCredito').click(function () {
        var numeroCarta = $('#numeroCarta')[0].value;
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/card/" + numeroCarta,
            success: function (risposta) {
                comunicaCredito(risposta);
            },
            error: function (stato) {
                comunicaErroreCredito(stato.status);
            }
        });
    });

    if (sessionStorage.getItem('privileges') != "admin") {
        $("#utenti").remove();
        $("#venditori").remove();
        $("#clienti").remove();
        $("#addCard").remove();
        $(".deleteCard").remove();
        $(".disableCard").remove();
    }

    if (sessionStorage.getItem('privileges') == "cliente") {
        $("#addTransaction").remove();
    }

    if ($("#dashboard").length > 0) {
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/general/counters",
            headers: {
                "SESSION-TOKEN": sessionStorage.getItem('token')
            },
            success: function (risposta) {
                mappaCounters(risposta);
            },
            error: function (stato) {
                $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function () { $(".btn-close")[0].click() }, 4000);
            }
        });
    }

    if ($("#transazioni").length > 0) {
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/transaction/list",
            headers: {
                "SESSION-TOKEN": sessionStorage.getItem('token')
            },
            success: function (risposta) {
                mappaTransazioni(risposta);
            },
            error: function (stato) {
                $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function () { $(".btn-close")[0].click() }, 4000);
            }
        });
    }

    if ($("#paginaClienti").length > 0) {
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/user/list/customers",
            headers: {
                "SESSION-TOKEN": sessionStorage.getItem('token')
            },
            success: function (risposta) {
                mappaClienti(risposta.customers);
            },
            error: function (stato) {
                $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function () { $(".btn-close")[0].click() }, 4000);
            }
        });
    }

    if ($("#paginaVenditori").length > 0) {
        $.ajax({
            method: "GET",
            url: "http://localhost:8080/user/list/merchants",
            headers: {
                "SESSION-TOKEN": sessionStorage.getItem('token')
            },
            success: function (risposta) {
                mappaVenditori(risposta.merchants);
            },
            error: function (stato) {
                $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function () { $(".btn-close")[0].click() }, 4000);
            }
        });
    }

    if ($("#carte").length > 0) {
        if (sessionStorage.getItem('privileges') != "admin") {
            $.ajax({
                method: "GET",
                url: "http://localhost:8080/card/list",
                headers: {
                    "SESSION-TOKEN": sessionStorage.getItem('token')
                },
                success: function (risposta) {
                    mappaCarte(risposta.cards);
                },
                error: function (stato) {
                    $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                    setTimeout(function () { $(".btn-close")[0].click() }, 4000);
                }
            });
        } else {
            $.ajax({
                method: "GET",
                url: "http://localhost:8080/card/listAll",
                headers: {
                    "SESSION-TOKEN": sessionStorage.getItem('token')
                },
                success: function (risposta) {
                    mappaCarte(risposta.cards);
                },
                error: function (stato) {
                    $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                    setTimeout(function () { $(".btn-close")[0].click() }, 4000);
                }
            });
        }
    }

    $("#inviaNuovaTransazione").bind("click", function (event) {
        let numeroCartaRegx = new RegExp("[0-9]{16}");
        let creditoRegx = new RegExp("[0-9]+\.?[0-9]{2}");
        if(document.getElementById("cardNumber").value=="" || !numeroCartaRegx.test(document.getElementById("cardNumber").value)){
            document.getElementById("cardNumber").focus();
            return;
        }
        if(document.getElementById("credit").value=="" || !creditoRegx.test(document.getElementById("cardNumber").value)){
            document.getElementById("credit").focus();
            return;
        }
        if(document.getElementById("operationType").value==""){
            document.getElementById("operationType").focus();
            return;
        }
        
        let credito = document.getElementById("credit").value;
        if(document.getElementById("operationType").value=="payment"){
            credito = "-"+credito;
        }

        $.ajax({
            method: "POST",
            url: "http://localhost:8080/transaction/create",
            headers: {
                "Content-Type": "application/json",
                "SESSION-TOKEN": sessionStorage.getItem('token')
            },
            data: JSON.stringify({
                "card_number": document.getElementById("cardNumber").value,
                "credit": credito
            }),
            success: function (risposta) {
                $("body").append("<div class=\"alert alert-info alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-info me-1\"></i>Transazione aggiunta!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function () { $(".btn-close")[0].click() }, 2000);
                window.location.href = "transactions.html";
            },
            error: function (stato) {
                $("body").append("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
                setTimeout(function () { $(".btn-close")[0].click() }, 4000);
            }
        });

    });

});


function checkLogin(risposta) {
    if (risposta.error) {
        $("#pagetitle").prepend("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i> " + risposta.errorMessage + "<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
        setTimeout(function () { $(".btn-close")[0].click() }, 2000);
    } else {
        sessionStorage.setItem('token', risposta.session.token);
        sessionStorage.setItem('privileges', risposta.session.privileges);
        setTimeout(function () { window.location.href = "index.html"; }, 1000);
    }
}

function comunicaCredito(risposta) {
    $('#bodyModale').empty();
    $('#bodyModale').append('<p>Il credito residuo della carta &egrave;: ' + risposta.card.credit + '&euro;</p>');
}

function comunicaErroreCredito(status) {
    $('#bodyModale').empty();
    if (status == 404) {
        $('#bodyModale').append('Carta non trovata');
        return;
    }
    $('#bodyModale').append('Si è verificato un errore si prega di riprovare.');
}

function mappaCounters(response) {
    response.counters.forEach(counter => {
        if (counter.reference == "CARD") {
            $("#numeroCarte").append("Numero di carte: " + counter.counter);
        }
        if (counter.reference == "USER") {
            $("#numeroUtentiTotale").append("Numero totale di utenti: " + (counter.counterAdmin + counter.counterCustomers + counter.counterMerchants));
            $("#numeroAdmin").append("Numero di admin: " + counter.counterAdmin);
            $("#numeroCustomers").append("Numero di clienti: " + counter.counterCustomers);
            $("#numeroMerchant").append("Numero di venditori: " + counter.counterMerchants);
        }
        if (counter.reference == "TRANSACTION") {
            $("#numeroTransazioni").append("Numero di transazioni: " + counter.counter);
            $("#creditoRicaricatoTotale").append("Credito totale ricaricato: " + counter.amountAccredit);
            $("#creditoPagatoTotale").append("Credito totale addebitato: " + counter.amountPayments);
        }
    });
}


function mappaTransazioni(response) {
    response.forEach(transazione => {
        $("#clone_transazioni_vacante").clone().attr("style", "")
        .find("#customer").html(transazione.customer).end()
        .find("#merchant").html(transazione.merchant).end()
        .find("#cardNumber").html(transazione.cardNumber).end()
        .find("#credit").html(transazione.credit + " &euro;").end().appendTo("#listaTransazioni");
    });
}

function mappaClienti(response) {
    response.forEach(cliente => {
        $("#clone_cliente_vacante").clone().attr("style", "").attr("id", cliente.id)
        .find("#email").html(cliente.email).end()
        .find("#name").html(cliente.name).end()
        .find("#surname").html(cliente.surname).end()
        .find("#numberOfCards").html(cliente.cards.length).end()
        .find("#bottoneCancellazione").bind("click", function (event) { deleteUser(cliente.id); }).end()
        .find("#bottoneCancellazione").attr("id", "cancella_"+cliente.id).end().appendTo("#listaClienti");
    });
}

function mappaVenditori(response) {
    response.forEach(venditore => {
        $("#clone_venditore_vacante").clone().attr("style", "").attr("id", venditore.id)
        .find("#email").html(venditore.email).end()
        .find("#name").html(venditore.name).end()
        .find("#surname").html(venditore.surname).end()
        .find("#numberOfCards").html(venditore.cards.length).end()
        .find("#bottoneCancellazione").bind("click", function (event) { deleteUser(venditore.id); }).end()
        .find("#bottoneCancellazione").attr("id", "cancella_" + venditore.id).end()
        .find("#checkboxAbilitazione").attr("checked", venditore.disabled).end()
        .find("#checkboxAbilitazione").bind("click", function (event) { bloccaSbloccaUser(venditore.email); }).end()
        .find("#checkboxAbilitazione").attr("id", "blocco_sblocco_" + venditore.id).end()
        .appendTo("#listaVenditori");
    });
}

function mappaCarte(response) {
    response.forEach(carta => {
        $("#clone_carta_vacante").clone().attr("style", "").attr("id", carta.number)
            .find("#cardNumber").html(carta.number).end()
            .find("#expiration").html(carta.expiration).end()
            .find("#transactionsNumber").html(carta.transactions.length).end()
            .find("#ownerEmails").html("NN").end()
            .find("#credit").html(carta.credit + " &euro;").end()
            .find("#bottoneCancellazione").bind("click", function (event) { deleteCarta(carta.number); }).end()
            .find("#bottoneCancellazione").attr("id", "cancella_" + carta.card_id).end()
            .find("#checkboxAbilitazione").attr("checked", carta.blocked).end()
            .find("#checkboxAbilitazione").bind("click", function (event) { bloccaSbloccaCarta(carta.number); }).end()
            .find("#checkboxAbilitazione").attr("id", "blocco_sblocco_" + carta.card_id).end()
            .appendTo("#listaCarte");
    });
}

function deleteCarta(number) {
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/card/delete",
        headers: {
            "Content-Type": "application/json",
            "SESSION-TOKEN": sessionStorage.getItem('token')
        },
        data: JSON.stringify({
            "number": number
        }),
        success: function (risposta) {
            $("#carte").append("<div class=\"alert alert-info alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-info me-1\"></i>Carta Eliminata!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
            setTimeout(function () { $(".btn-close")[0].click() }, 2000);
            $("#"+number).remove();
        },
        error: function (stato) {
            $("#carte").append("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
            setTimeout(function () { $(".btn-close")[0].click() }, 4000);
        }
    });
}

function bloccaSbloccaCarta(number) {
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/card/lockUnlock",
        headers: {
            "Content-Type": "application/json",
            "SESSION-TOKEN": sessionStorage.getItem('token')
        },
        data: JSON.stringify({
            "number": number
        }),
        success: function (risposta) {
            $("#carte").append("<div class=\"alert alert-info alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-info me-1\"></i>Stato della carta aggiornato!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
            setTimeout(function () { $(".btn-close")[0].click() }, 2000);
        },
        error: function (stato) {
            $("#carte").append("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
            setTimeout(function () { $(".btn-close")[0].click() }, 4000);
        }
    });
}

function deleteUser(id) {
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/user/delete",
        headers: {
            "Content-Type": "application/json",
            "SESSION-TOKEN": sessionStorage.getItem('token')
        },
        data: JSON.stringify({
            "id": id
        }),
        success: function (risposta) {
            $("body").append("<div class=\"alert alert-info alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-info me-1\"></i>Utente Eliminato!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
            setTimeout(function () { $(".btn-close")[0].click() }, 2000);
            $("#"+id).remove();
        },
        error: function (stato) {
            $("body").append("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
            setTimeout(function () { $(".btn-close")[0].click() }, 4000);
        }
    });
}

function bloccaSbloccaUser(email) {
    $.ajax({
        method: "POST",
        url: "http://localhost:8080/user/blockUnblock",
        headers: {
            "Content-Type": "application/json",
            "SESSION-TOKEN": sessionStorage.getItem('token')
        },
        data: JSON.stringify({
            "email": email
        }),
        success: function (risposta) {
            $("body").append("<div class=\"alert alert-info alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-info me-1\"></i>Stato del venditore aggiornato!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
            setTimeout(function () { $(".btn-close")[0].click() }, 2000);
        },
        error: function (stato) {
            $("body").append("<div class=\"alert alert-danger alert-dismissible fade show allerta\" role=\"alert\" ><i class=\"bi bi-exclamation-octagon me-1\"></i>Si &egrave; verificato un problema, controllare la connessione di rete e riprovare!<button type=\"button\" style=\"display:none;\" class=\"btn-close\" data-bs-dismiss=\"alert\" aria-label=\"Close\"></button></div>");
            setTimeout(function () { $(".btn-close")[0].click() }, 4000);
        }
    });
}