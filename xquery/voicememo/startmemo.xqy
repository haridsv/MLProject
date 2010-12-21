xquery version "1.0-ml";

<Response>
   <Say>Hello. Please start recording your voice memo after the beep. Press # key when done.</Say>
   <Record transcribe="true" transcribeCallback="transcribedmemo.xqy"
	   action="recordedmemo.xqy" maxLength="30" finishOnKey="#"/>
</Response>

