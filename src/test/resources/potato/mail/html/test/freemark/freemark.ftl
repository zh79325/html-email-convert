<html>
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" type="text/css" href="http://cdn.bootcss.com/twitter-bootstrap/3.3.6/css/bootstrap.min.css">
</head>
<body>
<div class="container">
    <legend><h1>${title}</h1></legend>
    <table class="table">
        <tr>
            <th>
                #
            </th>
            <th>
                Name
            </th>
            <th>
                Tag
            </th>
        </tr>
        <#list items as item>
        <tr>
            <td>${item.id}</td>
            <td>${item.name}</td>
            <td>
              <#if item_index%2==0>
                   <label class="label label-success">
                       ${item.tag}
                   </label>
              <#else>
                      <label class="label label-warning">
                          ${item.tag}
                      </label>

              </#if>
            </td>
        </tr>
        </#list>
    </table>
</div>
</body>
</html>