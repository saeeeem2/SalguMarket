document.getElementById(`profile-image-display`).addEventListener(`click`,()=>{
	document.getElementById(`profile-image-input`).click();
})

document.getElementById(`profile-image-input`).addEventListener(`change`,()=>{
	document.getElementById(`profile_submit`).submit();
})