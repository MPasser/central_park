//login.jsp jump to register.jsp
function jumpToRegister() {
    window.location = "register";
}

// jump to home page
function jumpToHomePage() {
    window.location = "/";
}

//refresh portrait preview
function refreshPortraitPreview() {
    let portraitFile = $('#portrait')[0].files[0];

    console.log("执行refreshPortraitPreview()");
    console.log("文件大小：" + portraitFile.size);

    // 检查文件的大小与格式
    if (portraitFile.size > 1024 * 3072) {
        alert("图片大小不能够超过3M");
        $('#portrait').val('');
        return false;
    }

    let filename = portraitFile.name;
    let dotIndex = filename.lastIndexOf(".");

    console.log("dotIndex：" + dotIndex);


    if (dotIndex === filename.length - 1 || -1 === dotIndex) {
        alert("图片格式不支持，支持的格式有[\"bmp\",\"gif\",\"jpeg\",\"jpg\",\"png\",\"psd\",\"webp\"]");
        $('#portrait').val('');
        return false;
    }
    let fileSuffixName = portraitFile.name.substring(dotIndex + 1).toLowerCase();
    console.log("文件后缀名：" + fileSuffixName);


    let fileFormats = ["bmp", "gif", "jpeg", "jpg", "png", "psd", "webp"];
    let flag = false;
    fileFormats.forEach(function (format, index) {
        if (format === fileSuffixName) {
            flag = true;
        }
        console.log("index:" + index + ",format:" + format + ",flag:" + flag);
    });

    // 如果通过了检查，则更新头像预览
    if (flag) {
        $('#portrait-preview').attr('src', window.URL.createObjectURL(portraitFile));
    } else {
        alert("图片格式不支持，支持的格式有[\"bmp\",\"gif\",\"jpeg\",\"jpg\",\"png\",\"psd\",\"webp\"]");
        $('#portrait').val('');
        return false;
    }
}


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
        $.post('findIfUserExists',
            {'username': username},
            function (data) {
                console.log(data);
                if ('usernameIsNull' === data) {
                    info.text('用户名不能为空');
                    info.css('color', '#dd4822');
                } else if ('usernameExists' === data) {
                    info.text('用户名已存在');
                    info.css('color', '#dd4822');
                } else if ('usernameNotExists' === data) {
                    info.text('用户名可用');
                    info.css('color', '#44bb5c');
                } else {
                    console.log('ajax接收的数据异常');
                }
            }
        );
        return true;
    }
}

// check if username exists
// FIXME : 由于$.post()方法没有返回值，其中的function是回调函数，故无法在调用的时候就返回结果，只能转而判断info
function checkUsernameExists() {
    let info = $('#username-form-info');
    if (info.text() === "用户名已存在") {
        return false;
    } else {
        return true;
    }
}

// validate password
function checkPassword(value, info) {
    let password = value;
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
        info.text('请重复密码');
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

// encode password
function encodePassword() {
    let password = $('#password').val();
    $('#old-password').val(md5($('#old-password').val()));
    $('#password').val(md5(password));
    $('#repassword').val(md5(password));
}

// checkAgain
function checkForm() {
    let flag = true;

    // FIXME : 这里使用逻辑表达式flag赋值无法得到预期的结果,有undefined值出现；我服了，下面的checkLoginForm()方法都可以

    if (!checkUsername()) {
        alert(checkUsername());
        console.log("username invalid");
        flag = false;
    }
    if (!checkPassword($('#password').val(), $('#password-form-info'))) {
        alert(checkPassword());
        console.log("password invalid");
        flag = false;
    }
    if (!checkRepassword()) {
        console.log("repassword invalid");
        flag = false;
    }
    if (!checkEmail()) {
        console.log("email invalid");
        flag = false;
    }

    // flag = checkUsername() && checkPassword() && checkRepassword() && checkEmail();
    // alert(flag);

    if (flag) {
        encodePassword();
    }
    return flag;
}

function resetPortrait(oldPortraitSrc) {
    $('#portrait').val('');
    $('#portrait-preview').attr('src', oldPortraitSrc);
}

/**
 * register form validation functions end
 */


/**
 * login form validation start
 */
//
function checkLoginUsername() {
    let username = $('#username').val();
    let info = $('#username-form-info');
    let reg = /^[\w]{2,15}$/;

    if (null == username || "" === username) {
        info.text('请输入用户名');
        info.css('color', '#dd4822');
        return false;
    } else if (!reg.test(username)) {
        info.text('用户名要求2-15位字符，且只含有数字、字母、下划线');
        info.css('color', '#dd4822');
        return false;
    } else {
        info.text('');
        return true;
    }
}

function checkLoginPassword() {
    let password = $('#password').val();
    let info = $('#password-form-info');
    if (null == password || "" === password) {
        info.text('请输入密码');
        info.css('color', '#dd4822');
        return false;
    } else {
        info.text('');
        return true;
    }
}

function checkLoginForm() {

    let flag;

    flag = checkLoginUsername() && checkLoginPassword();

    if (flag) {
        encodePassword();
    }
    return flag;
}

/**
 * login form validation end
 */


function submitForm(form) {
    form.submit();
}


/**
 * modify form validation start
 */

function checkModifyForm() {
    let flag = true;

    // FIXME : 这里使用逻辑表达式flag赋值无法得到预期的结果,有undefined值出现；我服了，下面的checkLoginForm()方法都可以

    if (!checkUsername()) {
        alert(checkUsername());
        console.log("username invalid");
        flag = false;
    }
    if (!checkEmail()) {
        console.log("email invalid");
        flag = false;
    }

    // flag = checkUsername() && checkPassword() && checkRepassword() && checkEmail();
    // alert(flag);
    return flag;
}

/**
 * modify form validation end
 */