# Configure Bitbucket Webhook

### Bitbucket Configuration
 1. On your bitbucket project, select the ***Settings > WORKFLOW > Webhooks > Add webhook*** option.
 2. Type the _title_ name for this webhook.
 3. Type the _URL_ of your jenkins installation suffixed by ***/bitbucket-hook/***, ex: ```https://jenkins.ciserver/bitbucket-hook/```
 4. Select status = _Active_.
 5. Select the option _Repository push_

### Jenkins Configuration
On the jenkins side, not much configuration is needed, however, be sure that the Bitbucket plugin is installed and then after creating a project, select the following options:

 - On ***Build Triggers***, select: 'Build when a change is pushed to BitBucket'.
 - Add an SCM Git Repository URL (This repository must be the one with the trigger we previously configured in Bitbucket settings).
 - Finish configuring the job according to your needs.
 - Finally, click on apply and save your job configuration.
