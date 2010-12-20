xquery version "1.0-ml";
declare namespace ht = "xdmp:http";

<Response>{
let $recordingUrl := xdmp:get-request-field("RecordingUrl", "")
return
if ($recordingUrl eq "") then
  (<Say>Something went wrong, please try again.</Say>,
  <Redirect>startmemo.xqy</Redirect>)
else
  let $callFrom := xdmp:get-request-field("From")
  let $catURIs := ('/voicememos', fn:concat('/voicememos/', $callFrom))
  let $binURI := fn:concat('/voicememo/recording/',
      xdmp:get-request-field("CallSid"), '.xml')
  return (
    let $response := xdmp:http-get($recordingUrl)
    return if ($response[1]//ht:code/text() eq "200") then
      xdmp:document-insert($binURI, $response[2]/node(), (),
          ('/voicememos/recordedmemo', $catURIs))
    else
      (),
    let $documentURI := fn:concat('/voicememo/',
        xdmp:get-request-field("CallSid"), '.xml')
    let $recordingDuration := xdmp:get-request-field("RecordingDuration", "0")
    return xdmp:document-insert($documentURI,
        <voicememo>
          <recordedAt>{fn:current-dateTime()}</recordedAt>
          <recordedVoiceDocURI>{$binURI}</recordedVoiceDocURI>
          <recordedDuration>{$recordingDuration}</recordedDuration>
          <transcriptionStatus>unavailable</transcriptionStatus>
        </voicememo>,
        (),
        $catURIs),
     (<Say>Your memo has been recorded.</Say>,
     <Say>Goodbye.</Say>)
   )
}</Response>
