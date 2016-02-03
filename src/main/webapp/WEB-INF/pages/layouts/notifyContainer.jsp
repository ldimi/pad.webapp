
<jsp:text>
    <![CDATA[
    <div id="notify-container"  class="ui-notify" style="z-index: 1000;display: none;" >
        <div id="default" >
            <h1>#{title}</h1>
            <p>#{text}</p>
        </div>
        <div id="error" style="background-color: red;" >
            <a class="ui-notify-close ui-notify-cross" href="#">X</a>
            <h1>#{title}</h1>
            <p>#{text}</p>
        </div>
    </div>
    ]]>
</jsp:text>

