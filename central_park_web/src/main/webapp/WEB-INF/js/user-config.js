/**
 * login.jsp jump to register.jsp start
 */
function jumpToRegister() {
    window.location="register";
}

/**
 * login.jsp jump to register.jsp end
 */



/**
 * register form validation functions start
 */

// validate username
function checkUsername() {
    let username = $('#username').val();
    let info = $('#username-form-info');
    let reg = /^[\w]{2,15}$/;

    if (null == username || "" === username) {
        info.text('用户名不能为空');
        info.css('color', '#dd4822');
        return false;
    } else if (!reg.test(username)) {
        info.text('用户名要求2-15位字符，且只含有数字、字母、下划线');
        info.css('color', '#dd4822');
        return false;
    } else {
        $.post('findUserByName',
            {'username': username},
            function (data) {
            console.log(data);
                if ('usernameIsNull' === data) {
                    info.text('用户名不可为空');
                    info.css('color', '#44bb5c');
                    return true;
                } else if ('usernameExists' === data) {
                    info.text('用户名已存在');
                    info.css('color', '#dd4822');
                    return false;
                } else if ('usernameNotExists' === data) {
                    info.text('用户名可用');
                    info.css('color', '#44bb5c');
                    return true;
                } else {
                    console.log('ajax接收的数据异常');
                }
            }
        );


    }
}

// validate password
function checkPassword() {
    let password = $('#password').val();
    let info = $('#password-form-info');
    let reg = /^[\w]{6,20}$/;

    if (null == password || "" === password) {
        info.text('密码不能为空');
        info.css('color', '#dd4822');
        return false;
    } else if (!reg.test(password)) {
        info.text('密码要求6-20位字符，且只含有数字、字母、下划线');
        info.css('color', '#dd4822');
        return false;
    } else {
        info.text('密码可用');
        info.css('color', '#44bb5c');
        return true;
    }


}

// validate repassword
function checkRepassword() {
    let password = $('#password').val();
    let repassword = $('#repassword').val();
    let info = $('#repassword-form-info');

    if (null == repassword || "" === repassword) {
        info.text('请输入密码');
        info.css('color', '#dd4822');
        return false;
    } else if (password !== repassword) {
        info.text('两次输入的密码不一致');
        info.css('color', '#dd4822');
        return false;
    } else {
        info.text('密码一致');
        info.css('color', '#44bb5c');
        return true;
    }
}

// validate email
function checkEmail() {
    let email = $('#email').val();
    let info = $('#email-form-info');
    let reg = /^[\da-z]+([\-\.\_]?[\da-z]+)*@[\da-z]+([\-\.]?[\da-z]+)*(\.[a-z]{2,})+$/i;

    if (null == email || "" === email) {
        info.text('');
        return true;
    } else if (!reg.test(email)) {
        info.text('邮箱格式不正确');
        info.css('color', '#dd4822');
        return false;
    } else {
        info.text('邮箱格式正确');
        info.css('color', '#44bb5c');
        return true;
    }
}
/**
 * register form validation functions end
 */