// vars/sendEmail.groovy
def call(Map args = [:]) {
    // required: args.to (comma-separated) 
    def to = args.to ?: (env.RECIPIENTS ?: '$DEFAULT_RECIPIENTS')
    def subject = args.subject ?: "${env.JOB_NAME} #${env.BUILD_NUMBER} - ${currentBuild?.currentResult ?: 'UNKNOWN'}"
    def body = args.body ?: "<p>Build <b>${env.JOB_NAME} #${env.BUILD_NUMBER}</b> finished with status: <b>${currentBuild?.currentResult}</b>.</p><p><a href='${env.BUILD_URL}'>Open build</a></p>"
    def attachments = args.attachments ?: 'npm-audit-report.json,trivy_scan_ouput.txt'

    emailext(
      to: to,
      subject: subject,
      mimeType: 'text/html',
      body: body,
      attachmentsPattern: attachments,
      attachLog: false
    )
}
