xquery version "1.0-ml";

let $name := xdmp:get-request-field("name", "")

return if ($name ne "") then
  <Response><Say>Hello {$name}</Say></Response>
else
	<Response>
	   <Say>Hello {$name}. Please start recording your voice memo after the beep. Press # key when done.</Say>
	   <Record transcribe="true" transcribeCallback="transcribedmemo.xqy"
		   action="recordedmemo.xqy" maxLength="30" finishOnKey="#"/>
	</Response>

