

## How to use to checkout a sub-directory of a project ? 

go to "Version Control Settings" -> "Create and attache new VCS root"
VCS root 	Checkout rules
Edit Checkout Rules
project SVN TRUNK

    +:deployment=>.


go to "Build Trigger" -> Add new trigger
	
Triggers a build after a VCS check-in is detected
no need for trigger Rules

## How to use user/password/misc prompt?

goto "Build Parameters" -> "Add new parameter"

    Kind = environment variable
    Diplay = Prompt
    Type = Password



