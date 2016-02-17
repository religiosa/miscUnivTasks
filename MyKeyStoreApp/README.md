Mobile Systems Security, spring 2015
Programming assignment 4
Jenny Tyrväinen

The application creates the keys when started, and shows a toast notification for it. The keys are created in a separate thread and passed to the main activity, when ready.

The message to be signed, is written to provided text field. The result changes to a representation of the Signature object, when the signing is successful. When verifying, user must provide the same text (or different, when verifying will return "false"). The signature is stored in the MainActivity and thus need not to be provided.

The solution could have been more elegant, but at least it does work. :D

