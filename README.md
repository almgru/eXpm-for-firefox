# Extended Profile Manager (eXpm) for Firefox
I prefer to isolate my web browsing and keep different profiles for different workflows. For example I have a separate profiles for banking, system administration and development. I also isolate websites that are heavy on tracking to separate profiles. This for example allows me to have a default private profile which does not store any history at all.

## Why not just use Firefox Multi-Account Containers?
I used [Firefox Multi-Account Containers](https://addons.mozilla.org/sv-SE/firefox/addon/multi-account-containers/) for a long time but found that the isolation is not as robust as using different profiles. With profiles, you can change cookie-settings, home page and any other settings that are available in the Firefox settings page. Another big reason is that you can have different extensions and addons with profiles, but that is not possible with containers. 

## What's wrong with the default Firefox profile manager?
So profiles are great! But managing them is very cumbersome using the default profile manager. For example, if you want some settings to be enabled for all the profiles you have to labourosly go through every profile. Wouldn't it be great if you could derive future profiles from a template profile? That's where this project comes in.

`eXpm-for-firefox` is a standalone Java program for managing Firefox profiles. Just as the default profile manager, it allows creating new profiles, deleting profiles and setting the default profile. However, it also let's you define templates and derive new profiles from that template, which greatly improves management of settings for profile. For example, maybe you use DuckDuckGo as your default search engine and want all your profiles to use that instead of Google. Then you just define a template, start up Firefox and make the necessary change, then close Firefox to save the template. You can then use this template when you create new profiles to apply the change to it.
