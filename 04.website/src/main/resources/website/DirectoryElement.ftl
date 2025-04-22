<#if ast.isText()>
	<p>${ast.getText().getSource()}</p>
<#elseif ast.isPic()>
	<img src="${ast.getName()}.png" height="${ast.getHeight().getSource()}" width="${ast.getWidth().getSource()}">
<#else>
	<a href="${ast.getName()}.html"><#if ast.isPresentTitle()>${ast.getTitle().getSource()}<#else>${ast.getName()}</#if></a>
</#if>
</br>
