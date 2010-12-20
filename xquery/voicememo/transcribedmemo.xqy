xquery version "1.0-ml";

let $documentURI := fn:concat('/voicememo/',
    xdmp:get-request-field("CallSid"), '.xml')
let $transcriptionStatus := xdmp:get-request-field("TranscriptionStatus")
let $transcriptionText := xdmp:get-request-field("TranscriptionText")
return (xdmp:node-replace(doc($documentURI)/voicememo/transcriptionStatus,
            <transcriptionStatus>{$transcriptionStatus}</transcriptionStatus>),
        xdmp:node-insert-child(doc($documentURI)/voicememo,
            <transcriptionText>{$transcriptionText}</transcriptionText>))
