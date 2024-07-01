console.log('fileRegister.js Join Success');

document.getElementById('fileTrigger1').addEventListener('click',()=>{
   document.getElementById('files1').click();
});

document.getElementById('fileTrigger2').addEventListener('click',()=>{
   document.getElementById('files2').click();
});

const regExp = new RegExp("\.(exe|sh|bat|js|dll|msi)$"); // 실행 파일들
const maxSize = 1024*1024*20;

function fileValidation(fileName, fileSize){
   if(regExp.test(fileName)){
      return 0;
   }else if(fileSize > maxSize){
      return 0;
   }else{
      return 1;
   }; 
};

// 메인 이미지 업로드
document.getElementById('files1').addEventListener('change', (e)=>{
   const fileObjectArray = document.getElementById('files1').files;
   // input 태그의 multiple : 배열
   console.log(fileObjectArray);

   // 파일 업로드 개수 제한 설정
   if(fileObjectArray.length > 1){
      alert("파일을 1개만 등록할 수 있습니다.");
      document.getElementById('files1').value = '';
      return;
   }

   const div = document.getElementById('fileZone1');

   div.innerHTML = "";
   // 이전에 파일 업로드를 했던 파일들이 있다면 초기화(제거)

   let fileRegisterButton = document.getElementsByClassName('fileRegisterButton');
   for (let button of fileRegisterButton) {
       button.disabled = false; // 한번 true가 되었다면 false로 복구(초기화)
   };

   let isOK = 1; // isOK : 모든 파일의 검증 결과
   let ul = `<ul class="list-group list-group-flush"><hr>`;
      for(let file of fileObjectArray){
         let validResult = fileValidation(file.name, file.size); // validResult : 개별 파일의 검증 결과
         isOK *= validResult; // 각 파일의 검증 결과를 isOK에 중첩

         ul += `<li class="list-group-item">`;
         ul += `<div class="mb-3">`;
         ul += `${validResult ? '<div class="text-success-emphasis" style="color: #28a745 !important;">업로드 가능</div>' :
         '<div class="text-danger-emphasis" style="color: #dc3545 !important;">업로드 불가능</div>'}`;
         ul += `${file.name}</div>`;
         ul += `<span style="color: ${validResult ? '#28a745' : '#dc3545'} !important;">${file.size}Byte</span>`;
         ul += `</li>`;
      };
   ul += `</ul><hr>`;
   div.innerHTML = ul;

   if(isOK == 0){ // 파일 중 validation을 통과하지 못한 것이 있다면...
      for (let button of fileRegisterButton) {
         button.disabled = true; // 버튼 비활성화
      }
   };
});

// 보조 이미지 업로드
document.getElementById('files2').addEventListener('change', (e)=>{
   const fileObjectArray = document.getElementById('files2').files;
   console.log(fileObjectArray);

   if(fileObjectArray.length > 10){
      alert("파일을 최대 10개까지 등록할 수 있습니다.");
      document.getElementById('files2').value = '';
      return;
   }

   const div = document.getElementById('fileZone2');

   div.innerHTML = "";

   let fileRegisterButton = document.getElementsByClassName('fileRegisterButton');
   for (let button of fileRegisterButton) {
       button.disabled = false;
   };

   let isOK = 1;
   let ul = `<ul class="list-group list-group-flush"><hr>`;
      for(let file of fileObjectArray){
         let validResult = fileValidation(file.name, file.size);
         isOK *= validResult;

         ul += `<li class="list-group-item">`;
         ul += `<div class="mb-3">`;
         ul += `${validResult ? '<div class="text-success-emphasis" style="color: #28a745 !important;">업로드 가능</div>' :
         '<div class="text-danger-emphasis" style="color: #dc3545 !important;">업로드 불가능</div>'}`;
         ul += `${file.name}</div>`;
         ul += `<span style="color: ${validResult ? '#28a745' : '#dc3545'} !important;">${file.size}Byte</span>`;
         ul += `</li>`;
      };
   ul += `</ul><hr>`;
   div.innerHTML = ul;

   if(isOK == 0){
      for (let button of fileRegisterButton) {
         button.disabled = true;
      }
   };
});