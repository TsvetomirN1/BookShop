const CONFIG =
    {
        DOMAIN: "127.0.0.1:8080",
        PATHS:{
            GENERAL:{
                login:"/login",
                registration:"/register",
                all_books:"/books",
                library:"/library",
            },
            USER:{
                my_books: "/user/books",
            },
            ADMIN:{
                statistic: "/admin/statistics",
                all_books: "/admin/all-books",
                add_book: "/admin/add-book",
                dashboard: "/admin/dashboard"
            },
            REST:{
                all_books:"/api/books/all",
                all_categories:"/api/categories/all",
                all_authors: "/api/authors/all"

            },
        },
        PAGINATION:{
            books:{
                showPag: true,
                booksOnPage: 5,
            }
        },
        VALIDATORS: {
            LOGIN:{
                username_length: 3,
                password_length: 4,
                letters_regex: /^[A-Za-z]+$/
            },
            REGISTER:{
                email_regex:/^(?:[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+\.)*[\w\!\#\$\%\&\'\*\+\-\/\=\?\^\`\{\|\}\~]+@(?:(?:(?:[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!\.)){0,61}[a-zA-Z0-9]?\.)+[a-zA-Z0-9](?:[a-zA-Z0-9\-](?!$)){0,61}[a-zA-Z0-9]?)|(?:\[(?:(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\.){3}(?:[01]?\d{1,2}|2[0-4]\d|25[0-5])\]))$/,
                first_name:3,
                last_name:4
            },
        }
    }


function purchase() {
    $('#run-card-modal').on('click', e => {
        let count_price = 0
        let wishlist = JSON.parse(localStorage.getItem('wishlist'))
        $('#order_list_wishes').html(' ')
        for (let i = 0; i < wishlist.length; i++) {
            count_price += all_books[wishlist[i]].price
            $('#order_list_wishes').append(
                `<li>
                         <figure class="prod_img">
                             <img src="${all_books[wishlist[i]].image}"  style="width:80px"/>
                         </figure>
                         <div class="prod_info">
                             <div class="name ui-corner-left">${all_books[wishlist[i]].title}</div>
                             <div class="name text-black-50 m-0" style="font-size: 0.7vw; font-style: italic">${all_books[wishlist[i]].author}</div>
                             <div class="price">${all_books[wishlist[i]].price}</div>
                         </div>
                    </li>`
            )
        }
        $('.total_price').html(`<dt class="text-black-50 text-lg-end text-decoration-underline" style="font-size: 1.2vw">Total</dt><dd>${count_price.toFixed(2)}</dd>`)
    })
}

function display_wishes(){
    let wishlist = JSON.parse(localStorage.getItem('wishlist'))
    $('#wishlist_table').html(' ')
    for (let i = 0; i < wishlist.length; i++) {
        $('#wishlist_table').append(`
               <tr>
                   <td class="w-25">
                       <img src="${all_books[wishlist[i]].image}" style="height:80px" class="img-fluid img-thumbnail" alt="Sheep">
                   </td>
                   <td>${all_books[wishlist[i]].title}</td>
                   <td>${all_books[wishlist[i]].price}</td>
                   <td class="qty"><input type="text" class="form-control" onchange="calc(this.value, ${all_books[wishlist[i]].price}, ${i})" id="price-single" value="1"></td>
                   <td><div id="calc-price-${i}">${all_books[wishlist[i]].price}</div></td>
                   <td>
                       <a href="#" class="btn btn-danger btn-sm" onclick="remove_wish(${i})">
                           <i class="fa fa-times"></i>
                       </a>
                   </td>
               </tr>`
        )
    }
}

function calc(price, other, i){
    $('#calc-price-' + i).html((price * other).toFixed(2))
}

function remove_wish(index){
    let new_wishes = []
    let wish = JSON.parse(localStorage.getItem("wishlist"))
    for (let i = 0; i < wish.length; i++) {
        if(index === i){
            continue;
        }
        new_wishes.push(wish[i])
    }
    localStorage.setItem('wishlist', JSON.stringify(new_wishes))
    $('#wish-count').html(new_wishes.length)
    $("#wish-total").html(' ')
    display_wishes()
}


function wishes(){
    if(!wishlist){
        localStorage.setItem('wishlist', JSON.stringify([]))
    }
    else{
        $('#wish-count').html(wishlist.length)
    }


    $('#wish-modal').on('click', e=>{
        display_wishes()
    })

    setInterval(e=>{
        let wish = JSON.parse(localStorage.getItem('wishlist'))
        let wish_prices = []
        for (let i = 0; i < wish.length; i++) {
            wish_prices.push(parseFloat($(`#calc-price-` + i).text()))
        }
        if(wish_prices.length > 0){
            $("#wish-total").html(`Total: ` + (wish_prices.reduce((a,b) => a+b).toFixed(2)))
        }
        else{
            $("#wish-total").html()
        }
    })
}


function basket(){
    const d = document,
        cShake = document.getElementById("card-shake"),
        ccForm = d.querySelector(".card-form"),
        cNumber = d.querySelectorAll(".card-number"),
        ccNumber = ccForm.querySelector("#card-number"),
        ccMonth = ccForm.querySelector("#expires-month"),
        cMonth = d.querySelectorAll(".card__exp-month"),
        ccYear = ccForm.querySelector("#expires-year"),
        cYear = d.querySelectorAll(".card__exp-year"),
        ccCCV = ccForm.querySelector("#card-cvc"),
        cCCV = d.querySelectorAll(".card__cvc-2"),
        defaultNumberN = cNumber[0].querySelectorAll(".card__span")[0].innerHTML,
        defaultNumberM = cMonth[0].querySelectorAll(".card__span")[0].innerHTML,
        defaultNumberY = cYear[0].querySelectorAll(".card__span")[0].innerHTML,
        defaultNumberC = cCCV[0].querySelectorAll(".card__span")[0].innerHTML

    payment()

    function payment (ev){
        ev = ev || window.event

        let cardNumber, cardCCV
        addEvent(ccNumber, "focus", function (){
            cShake.classList.add("wrong-entry")
        })
        addEvent(ccNumber, "blur", function (){
            cShake.classList.remove("wrong-entry")
        })

        addEvent(ccMonth, "focus", function (){
            cShake.classList.add("wrong-entry")
        })
        addEvent(ccMonth, "blur", function (){
            cShake.classList.remove("wrong-entry")
        })

        addEvent(ccYear, "focus", function (){
            cShake.classList.add("wrong-entry")
        })
        addEvent(ccYear, "blur", function (){
            cShake.classList.remove("wrong-entry")
        })

        addEvent(ccCCV, "focus", function (){
            cShake.classList.add("wrong-entry")
        })
        addEvent(ccCCV, "blur", function (){
            cShake.classList.remove("wrong-entry")
        })

        addEvent(ccNumber, "keyup", function (){
            cardNumber = this.value.replace(/[^0-9\s]/g,'')

            if (!!this.value.match(/[^0-9\s]/g)){
                this.value = cardNumber
            }
            parts = numSplit(cardNumber.replace(/\s/g,''), [4,4,4,4])
            cardNumber = parts.join(' ')
            if (cardNumber != this.value){
                this.value = cardNumber
            }
            if (!cardNumber){
                cardNumber = defaultNumberN
            }

            syncText(cNumber, cardNumber)
        })

        addEvent(ccMonth, "keyup", function (){
            let month = this.value.replace(/[^0-9]/g,'')
            if (ev.keyCode == 38){
                if (!month){month = 0}
                month = parseInt(month)
                month++
                if (month < 10){
                    month = "0"+month
                }
            }

            if (ev.keyCode == 40){
                if (!month){month = 13}
                month = parseInt(month)
                month--
                if (month == 0){ month = 1}
                if (month < 10){
                    month = "0"+month
                }
            }

            if (parseInt(month) > 12){month = 12}
            if ( parseInt(month) < 1 && month != 0){month = "01"}
            if (month == "00"){month = "01"}
            if (month >= "2" && month <= "9"){
                month = "0"+month
            }
            if (month != this.value){
                this.value = month
            }
            if (!month){
                month = defaultNumberM
            }

            syncText(cMonth, month)
        })

        addEvent(ccYear, "keyup", function (){
            let currentYear = new Date().getFullYear().toString().substr(2,2),
                year = this.value.replace(/[^0-9]/g,'')
            if (ev.keyCode == 38){
                if (!year){year = currentYear}
                year = parseInt(year)
                year++
                if (year < 10){
                    year = "0"+year
                }
            }

            if (ev.keyCode == 40){
                if (!year){
                    year = parseInt(currentYear) + 5
                }
                year = parseInt(year)
                year--
                if (year < 10){
                    year = "0"+year
                }
            }

            if (year.toString().length == 2 && parseInt(year) < currentYear){
                year = currentYear
            }
            if (year != this.value){
                this.value = year
            }
            if (year > (parseInt(currentYear) + 5)){
                year = (parseInt(currentYear) + 5)
                this.value = year
            }
            if (!year){
                year = defaultNumberY
            }

            syncText(cYear, year)
        })

        addEvent(ccCCV, "keyup", function (){
            cardCCV = this.value.replace(/[^0-9\s]/g,'')

            if (cardCCV != this.value){
                this.value = cardCCV
            }
            if (!cardCCV){
                cardCCV = defaultNumberC
            }

            syncText(cCCV, cardCCV)
        })
    }

    function addEvent (elem, event, func){
        elem.addEventListener(event, func)
    }

    function syncText (elCol, text){
        let collection
        for (let j=0; j < elCol.length; j++){
            collection = elCol[j].querySelectorAll(".card__span")
            if (!collection.length){
                elCol[j].innerHTML = text
            } else{
                for (let i=0; i < collection.length; i++){
                    collection[i].innerHTML = text
                }
            }
        }
    }

    function numSplit(number, indexes){
        let tempArr = number.split(''),
            parts = []
        for (var i=0, l = indexes.length; i < l; i++){
            if (tempArr.length){
                parts.push(tempArr.splice(0,indexes[i]).join(''))
            }
        }
        return parts;
    }
}


const books_template =
    (book, hide_buttons = false) => {
        $('#books').html(' ')
        for (let i = 0; i < book.length; i++) {
            let buttons =""
            if(!hide_buttons){
                buttons = ` <div class="row">
                             <div class="col-sm-4">
                                 <div>
                                    <a role="button" href="/cart/add/95695292/" id="add-cart" class="btn btn-primary btn-block visible-xs">Add to Cart</a> 
                                 </div>
                             </div>
                             <div class="col-sm-8">
                                 <a onclick="add_wishlist(${book[i].id -1})" id="add-to-wishlist-${i}" class="btn btn-secondary btn-block visible-xs">Add to Wishlist</a>
                             </div>
                        </div>`
            }
            $('#books').append(`
                        <div class="row m-4 bg-dark p-4"  style="box-shadow: 5px 10px 18px #000;">
                            <div class="col-xs-5 col-sm-3 padding-right-none text-center">
                            <img class="cover cover-md lazy col-11" src="${book[i].image}">
                                    <div class="hidden-xs price"><strong>${book[i].price} ????.</strong></div>
                                </div>
                                <div class="col-xs-7 col-sm-9">
                                    <div class="row">
                                        <div class="col-xs-12">
                                            <h3 class="title" style="text-shadow: 2px 2px #86675D, 3px 3px #86675D; "><strong>${book[i].title}</strong></h3>

                                        </div>
                                    </div>
                                    <div class="row">
                                        <!---->
                                        <div class="col-xs-12"><span>${book[i].author}</span> (<span>${book[i].year}</span>)
                                        </div>
                                        <div class="visible-xs col-xs-12 col-sm-3 price"><span>${book[i].price} ????.</span></div>
                                    </div>
                                    <div class="hidden-xs row description">
                                        <div class="col-sm-12 text-white-50 m-1">
                                            <p>${book[i].description}</p>
                                            ${buttons}
                                        </div>
                                    </div>
                                </div>
                            </div>`)
        }
    }
const books_carousel =
    books => {
        for (let i = 0; i < books.length; i++) {
            $('.swiper-wrapper').append(
                `<div class="swiper-slide">
                 <div class="cards">
                     <div class="card p-2 bg-dark m-2">
                         <img class="img-thumbnail img-fluid"src="${books[i].image}"/>
                     </div>
                </div>
            </div>`
            )
        }
    }

const getParam =
    (param, location = window.location.search) => (location.match(new RegExp('[?&]' + param + '=([^&]+)')) || [, null])[1];
const valid_email =
    email => CONFIG.VALIDATORS.REGISTER.email_regex.test(email)
const valid_username = username =>
    username.length >= CONFIG.VALIDATORS.LOGIN.username_length && CONFIG.VALIDATORS.LOGIN.letters_regex.test(username)
const valid_password = password =>
    password.length >= CONFIG.VALIDATORS.LOGIN.password_length
const valid_fname = first_name =>
    first_name.length >= CONFIG.VALIDATORS.REGISTER.first_name && CONFIG.VALIDATORS.LOGIN.letters_regex.test(first_name)
const valid_lname = last_name =>
    last_name.length >= CONFIG.VALIDATORS.REGISTER.last_name && CONFIG.VALIDATORS.LOGIN.letters_regex.test(last_name)
const get_url = window.location.pathname

const filter = object => object.filter((i,j) => object.indexOf(i) === j)

function format_message(text, type){
    scroll(0,0)
    $("#messages").fadeIn(0)
    $('#messages').removeClass("alert-success");
    $('#messages').removeClass("alert-danger");
    $('#messages').addClass("alert-" + type);
    $('#messages').html(text)
    $("#messages").delay(1000).fadeOut(200)

}

switch(get_url){

//******** GENERAL MODULES *****************************************/////

// Registration Module --------------------------------------------------
    case CONFIG.PATHS.GENERAL.registration:
        setInterval(e=>{
            $('#register').prop("disabled", true);
            $("#fname-error").html("")
            $("#lname-error").html("")
            $("#email-error").html("")
            $("#user-error").html("")
            $("#pass-error").html("")
            $("#rpass-error").html("")
            if(!valid_fname($("#firstName").val())){
                $("#fname-error").html("<h5>First Name is required!</h5>")
            }
            else if(!valid_lname($("#lastName").val())){
                $("#lname-error").html("<h5>Last Name is required!</h5>")
            }
            else if(!valid_email($("#email").val())){
                $("#email-error").html("<h5>Email is required!</h5>")
            }
            else if(!valid_username($("#username").val())){
                $("#user-error").html("<h5>Username is required!</h5>")
            }
            else if(!valid_password($("#password").val())){
                $("#pass-error").html("<h5>Password is required!</h5>")
            }
            else if(!($("#confirmPassword").val() == $("#password").val())){
                $("#rpass-error").html("<h5>Passwords do not match!</h5>")
            }
            else{
                $('#register').prop("disabled", false);
            }
        }, 1)

        break;

// Login Module --------------------------------------------------
    case CONFIG.PATHS.GENERAL.login:
        setInterval(e=>{
            $('#login').prop("disabled", true);
            $("#pass-error").html("")
            $("#user-error").html("")
            if(!valid_username($("#username").val())){
                $("#user-error").html("<h5>Username is required!</h5>")
            }
            else if(!valid_password($("#password").val())){
                $("#pass-error").html("<h5>Password is required!</h5>")
            }
            else{
                $('#login').prop("disabled", false);
            }
        }, 1)
        break;

//************** ADMIN MODULES *******************************************///////
// Add Books  --------------------------------------------------
    case CONFIG.PATHS.ADMIN.add_book:

        $.ajax({
            url:CONFIG.PATHS.REST.all_books,
            type:"GET",
            success: response =>{
                let authors = []
                let categories = []
                for(let i=0; i < response.length; i++){
                    authors.push(response[i].author)
                    categories.push(response[i].category)
                }
                $("#category").autocomplete({
                    source: filter(categories)
                })

                $("#author").autocomplete({
                    source: filter(authors)
                })
            }
        })

        break;

// Dashboard  --------------------------------------------------
    case CONFIG.PATHS.ADMIN.dashboard:
        google.charts.load('current', {'packages':['table', 'controls']});
        google.charts.setOnLoadCallback(drawTable);

    function drawTable() {
        let books=  [['Cover','Title', 'Category', 'Author',  'Price', 'Published']]
        let prices = []
        let years = []

        $.get(CONFIG.PATHS.REST.all_books).done(response=>{
            for(let i=0; i < response.length; i++){
                books.push([
                    `<a target='_blank' href='https://www.ebooks.com/en-gb/book/${response[i].image.split("/")[6]}/'><img width='50px' src='${response[i].image}'></a>`,
                    response[i].title,
                    response[i].category,
                    response[i].author,
                    parseFloat(response[i].price),
                    String(response[i].year),
                ])
                prices.push(response[i].price)
                years.push(response[i].year)
            }

            var data = new google.visualization.arrayToDataTable(books);
            var dash = new google.visualization.Dashboard(document.getElementById('all-books'));


            var book_category = new google.visualization.ControlWrapper({
                'controlType': 'CategoryFilter',
                'containerId': 'book-genre',
                'options': {
                    'filterColumnLabel': 'Category',
                    'ui': {
                        'label' : '',
                        'caption' : 'By Category',
                        'labelStacking': 'horizontal',
                        'allowTyping': false,
                        'allowMultiple': true
                    }
                }
            });

            var book_year = new google.visualization.ControlWrapper({
                'controlType': 'CategoryFilter',
                'containerId': 'book-year',
                'options': {
                    'filterColumnLabel': 'Published',
                    'ui': {
                        'label' : 'Published on',
                        'caption' :'Year',
                        'labelStacking': 'horizontal',
                        'allowTyping': false,
                        'allowMultiple': false
                    }
                }
            });
            var book_name = new google.visualization.ControlWrapper({
                controlType: 'StringFilter',
                containerId: 'book-name',
                options: {
                    allowHtml: true,
                    filterColumnLabel: 'Title',
                    'ui': {
                        'label' : 'By Title',
                        'cssClass': 'book-filter'
                    }
                }
            });
            var book_price = new google.visualization.ControlWrapper({
                'controlType': 'NumberRangeFilter',
                'containerId': 'book-sold',
                'options': {
                    'filterColumnLabel': 'Price',
                    'minValue':Math.min(...prices) ,
                    'maxValue': Math.max(...prices)
                }
            });
            var book_author = new google.visualization.ControlWrapper({
                controlType: 'StringFilter',
                containerId: 'book-author',
                options: {
                    allowHtml: true,
                    filterColumnLabel: 'Author',
                    'ui': {
                        'label' : 'By Author',
                        'cssClass': 'book-filter'
                    }
                }
            });

            var table = new google.visualization.ChartWrapper({
                'chartType': 'Table',
                'containerId': 'all-books',
                'options':
                    {
                        allowHtml: true,
                        showRowNumber: true,
                        width: '100%',
                        height: '100%',
                        pagingButtonsConfiguration: 'auto',
                        pageSize: 10,
                        page: 'enable',
                        pagingSymbols: {
                            prev: 'prev',
                            next: 'next',
                        },
                        cssClassNames:{
                            headerRow: "g-table-header",
                            tableRow:"g-table-row",
                            selectedTableRow: "g-table-row-selected",
                            hoverTableRow : "g-table-row-hover",
                            headerCell: "g-table-header-cell",
                            tableCell: "g-table-cell",
                        }
                    }});
            dash.bind([book_name, book_author, book_price,book_year,  book_category], [table])
            dash.draw(data)

            google.visualization.events.addListener(table, 'select', tableSelectHandler);

            function tableSelectHandler(){
                let selectedItem = table.getChart().getSelection()[0];
                let true_selected =  table.getDataTable().getTableRowIndex(selectedItem.row)
                console.log(parseInt(true_selected) +1)
                if(true_selected !== null){
                    location.href = "/admin/edit-book/" + (parseInt(true_selected) +1)
                }
            }
        })
    }
        break;

// All Books  --------------------------------------------------a
    case CONFIG.PATHS.ADMIN.all_books:


        $.get(CONFIG.PATHS.REST.all_authors).done(response=> {
            localStorage.setItem('authors', JSON.stringify(response))
        })
        $.get(CONFIG.PATHS.REST.all_categories).done(response=> {
            localStorage.setItem('categories', JSON.stringify(response))
        })
        $.get(CONFIG.PATHS.REST.all_books).done(response=> {
            localStorage.setItem('books', JSON.stringify(response))
        })

        let authors = JSON.parse(localStorage.getItem('authors'))
        let cat = JSON.parse(localStorage.getItem('categories'))
        let books  = JSON.parse(localStorage.getItem('books'))

        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(barChart);
        google.charts.setOnLoadCallback(years_chart);
        google.charts.setOnLoadCallback(price_range);
        google.charts.setOnLoadCallback(authors_pie);

        let years = []
        for (let i = 0; i < books.length ; i++) {
            if(!years.includes(books[i].year) && books[i].year> 1995){
                years.push(parseInt(books[i].year))
            }
        }

        years.sort().reverse()

    function get_header(title){
        let header = [title]
        for (let i = 0; i < cat.length; i++) {
            header.push(cat[i].category)
        }
        return header
    }
    function authors_pie() {
        let header = get_header('Genre')
        let row = [['Genre', 'Authors']]
        for (let i = 1; i < header.length; i++) {
            let authors = []
            for (let k = 0; k < books.length; k++) {
                if(books[k].category === header[i] && !authors.includes(books[k].author)){
                    authors.push(authors)
                }
            }
            row.push([header[i],authors.length])
        }

        var data = google.visualization.arrayToDataTable(row);

        var options = {
            title: 'Authors per Category',
            is3D: true,
        };

        var chart = new google.visualization.PieChart(document.getElementById('authors_pie'));

        chart.draw(data, options);
    }
    function barChart() {

        let chart_by_cat = [['Genre', 'Books']]
        for (let i = 0; i < cat.length; i++) {
            let count =0
            for (let j = 0; j < books.length; j++) {
                if(books[j].category ===  cat[i].category){
                    count++;
                }
            }
            chart_by_cat.push([cat[i].category, count])
        }

        var data = google.visualization.arrayToDataTable(chart_by_cat);

        var options = {
            title: 'Books by Categories',
            height: 600,
        };
        var chart = new google.visualization.BarChart(document.getElementById('books_chart'));
        chart.draw(data, options);
    }
    function price_range() {

        let header = get_header('Years')
        let rows = [header]
        for (let i = 0; i < years.length ; i++) {
            let cols = [years[i]]
            for (let j = 1; j < header.length; j++) {
                let prices= 0
                for (let k = 0; k < books.length; k++) {
                    if(books[k].category === header[j] && books[k].year === years[i]){
                        prices += parseFloat(books[k].price)
                    }
                }
                cols.push(parseFloat(prices.toFixed(2)))
            }
            rows.push(cols)
        }

        var data = google.visualization.arrayToDataTable(rows);

        var options = {
            title : 'Books Prices by Publication Year',
            vAxis: {title: 'Prices'},
            hAxis: {title: 'Years'},
            seriesType: 'bars',
            height: 600,
            bar: {
                groupWidth: 100,
            },
            backgroundColor: '#fff',
            series: {
                5: {
                    type:'line',
                }
            }
        };

        var chart = new google.visualization.ComboChart(document.getElementById('price_range'));
        chart.draw(data, options);
    }

    function years_chart(){
        let header = get_header('Genre')
        let rows = [header]
        for (let i = 0; i < years.length ; i++) {
            let cols = [years[i]]
            for (let j = 1; j < header.length; j++) {
                let count= 0
                for (let k = 0; k < books.length; k++) {
                    if(books[k].category === header[j] && books[k].year === years[i]){
                        count ++;
                    }
                }
                cols.push(count)
            }
            rows.push(cols)
        }
        var data = google.visualization.arrayToDataTable(rows);

        var view = new google.visualization.DataView(data);

        var options = {
            title : 'Genres by Year of Publication',
            legend: {  maxLines: 3 },
            bar: { groupWidth: '75%' },
            isStacked: true
        };
        var chart = new google.visualization.BarChart(document.getElementById("years_chart"));
        chart.draw(view, options);
    }
        let kos = document.getElementsByTagName('rect')
        for (let i = 0; i < kos.length; i++) {
            kos[i].setAttribute("stroke-width", 50);
        }
        break;

//************** USER MODULES *******************************************///////
// My Books  --------------------------------------------------
    case CONFIG.PATHS.USER.my_books:
        let wishes = JSON.parse(localStorage.getItem('wishlist'));
        $.get(CONFIG.PATHS.REST.all_books).done(books=> {
            let wish_books = []

            for (let i = 0; i < wishes.length; i++) {
                wish_books.push(books[wishes[i]])
            }
            if (wish_books.length > 0) {
                books_template(wish_books, true)
            }
        })
        break;
}