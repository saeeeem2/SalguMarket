console.log("SafeTrade.js Join Success");

if (typeof window.ethereum !== 'undefined') { // MetaMask 연동 확인    
    console.log('It is connected to MetaMask!');
} else { // MetaMask와 연결이 안된 상태면 MetaMask 설치 웹페이지로 새로운 창을 오픈
    alert('MetaMask를 설치해 주세요.');
	window.history.back();
    window.open('https://chrome.google.com/webstore/detail/metamask/nkbihfbeogaeaoehlefnkodbefgpgknn?hl=ko');
}

const web3 = new Web3(window.ethereum); // MetaMask와 상호작용하는 Web3 객체 생성
const contractAddress = "0xE9168BAb3dbEe20593a1796e1Ee612f4861af696";
// 이더리움 네트워크에 배포된 스마트 계약의 주소
// <<< 주의 사항 >>> : Ganache에서 새로운 Workspace에 스마트 계약을 배포할 때마다 값을 수정해야 함
const contractABI = [
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "id",
				"type": "uint256"
			},
			{
				"indexed": false,
				"internalType": "address",
				"name": "approver",
				"type": "address"
			}
		],
		"name": "CancellationApproved",
		"type": "event"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "id",
				"type": "uint256"
			},
			{
				"indexed": false,
				"internalType": "address",
				"name": "requester",
				"type": "address"
			}
		],
		"name": "CancellationRequested",
		"type": "event"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "id",
				"type": "uint256"
			},
			{
				"indexed": false,
				"internalType": "address",
				"name": "seller",
				"type": "address"
			},
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "price",
				"type": "uint256"
			}
		],
		"name": "ProductCreated",
		"type": "event"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "id",
				"type": "uint256"
			},
			{
				"indexed": false,
				"internalType": "address",
				"name": "buyer",
				"type": "address"
			},
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "price",
				"type": "uint256"
			}
		],
		"name": "ProductPurchased",
		"type": "event"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "id",
				"type": "uint256"
			},
			{
				"indexed": false,
				"internalType": "address",
				"name": "buyer",
				"type": "address"
			}
		],
		"name": "PurchaseCancelled",
		"type": "event"
	},
	{
		"anonymous": false,
		"inputs": [
			{
				"indexed": false,
				"internalType": "uint256",
				"name": "id",
				"type": "uint256"
			},
			{
				"indexed": false,
				"internalType": "address",
				"name": "buyer",
				"type": "address"
			}
		],
		"name": "PurchaseConfirmed",
		"type": "event"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "_id",
				"type": "uint256"
			}
		],
		"name": "approveCancel",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "",
				"type": "uint256"
			}
		],
		"name": "buyers",
		"outputs": [
			{
				"internalType": "address",
				"name": "",
				"type": "address"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "",
				"type": "uint256"
			}
		],
		"name": "cancelRequests",
		"outputs": [
			{
				"internalType": "bool",
				"name": "requested",
				"type": "bool"
			},
			{
				"internalType": "bool",
				"name": "approved",
				"type": "bool"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "_id",
				"type": "uint256"
			}
		],
		"name": "confirmPurchase",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "_price",
				"type": "uint256"
			}
		],
		"name": "createProduct",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [],
		"name": "productCount",
		"outputs": [
			{
				"internalType": "uint256",
				"name": "",
				"type": "uint256"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "",
				"type": "uint256"
			}
		],
		"name": "products",
		"outputs": [
			{
				"internalType": "uint256",
				"name": "id",
				"type": "uint256"
			},
			{
				"internalType": "address payable",
				"name": "seller",
				"type": "address"
			},
			{
				"internalType": "uint256",
				"name": "price",
				"type": "uint256"
			},
			{
				"internalType": "bool",
				"name": "isSold",
				"type": "bool"
			}
		],
		"stateMutability": "view",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "_id",
				"type": "uint256"
			}
		],
		"name": "purchaseProduct",
		"outputs": [],
		"stateMutability": "payable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "_id",
				"type": "uint256"
			}
		],
		"name": "requestCancel",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	},
	{
		"inputs": [
			{
				"internalType": "uint256",
				"name": "_id",
				"type": "uint256"
			}
		],
		"name": "sellerCancel",
		"outputs": [],
		"stateMutability": "nonpayable",
		"type": "function"
	}
];

// 스마트 계약 인스턴스 생성
const contract = new web3.eth.Contract(contractABI, contractAddress);

// MetaMask와 연동된 계정 가져오기 함수
async function getAccount() {
    const accounts = await ethereum.request({ method: 'eth_requestAccounts' });
	// 영문자는 소문자로 치환하여 accounts에 반환한다.
    return accounts[0]; // 현재 MetaMask에서 선택되어 있는 계정을 반환
}

// 대문자를 소문자로 치환하는 함수
function CustomToLowerCase(text){
	return text.replace(/[A-Z]/g, function(match){
		return match.toLowerCase();
	});
};
loginUserWalletAddress = CustomToLowerCase(loginUserWalletAddress);
console.log('>>> 살구마켓 로그인 사용자의 지갑 주소(소문자 치환) >>> ', loginUserWalletAddress);

// 참고 사항 : 모든 거래 단위는 ether 단위로 통일

// 판매자의 제품 판매 등록
const isCreateProductButton = document.getElementById('createProduct');
// HTML의 Button 요소 가져오기 (id가 createProduct인 요소가 없으면 Null 반환)
if(isCreateProductButton){ // 만약 id가 createProduct인 요소가 있다면...
	console.log('Event(createProduct) activate OK');
	document.getElementById('createProduct').addEventListener('click', async () => {
		const account = await getAccount();
	
		// 웹사이트 로그인 사용자의 지갑 주소가 현재 MetaMask의 지갑 주소와 동일한지 확인
		if(loginUserWalletAddress == account){
			const price = web3.utils.toWei(document.getElementById('createProductValue').value, 'ether');
	
			contract.methods.createProduct(price).send({ from: account })
				.on('receipt', function(receipt){
					// 블록 생성 성공 상황
					console.log('Product created:', receipt);
					document.getElementById('createProductForm').submit();
				})
				.on('error', function(error) {
					// 블록 생성 에러 상황
					console.error('Transaction failed:', error)
					window.location.href = '/';
				});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		}
	});
};

// 구매자의 구매 신청
const isPurchaseProductButton = document.getElementById('purchaseProduct');
if(isPurchaseProductButton){
	console.log('Event(purchaseProduct) activate OK');
	document.getElementById('purchaseProduct').addEventListener('click', async () => {
		const account = await getAccount();
		
		if(loginUserWalletAddress == account){
			const productId = document.getElementById('purchaseProductId').value;
			const price = web3.utils.toWei(document.getElementById('purchaseProductPrice').value, 'ether');
			// 제품 번호와 가격 정보가 HTML에 전송되어 있어야 함
			
			contract.methods.purchaseProduct(productId).send({ from: account, value: price })
				.on('receipt', function(receipt){
					console.log('Product purchased:', receipt);
					updateProductState1(productId);
				})
				.on('error', function(error) {
					console.error('Transaction failed:', error)
					window.location.href = '/';
				});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
};

// 구매자의 구매 확정
const isConfirmPurchaseButton = document.getElementById('confirmPurchase');
if(isConfirmPurchaseButton){
	console.log('Event(confirmPurchase) activate OK');
	document.getElementById('confirmPurchase').addEventListener('click', async () => {
		const account = await getAccount();
	
		if(loginUserWalletAddress == account){
			const productId = document.getElementById('confirmPurchaseValue').value;
			contract.methods.confirmPurchase(productId).send({ from: account })
				.on('receipt', function(receipt){
					console.log('Purchase confirmed:', receipt);
					updateProductState2(productId);
				})
				.on('error', function(error) {
					console.error('Transaction failed:', error)
					window.location.href = '/';
				});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
};

// 구매자가 판매자에게 거래 취소를 요청
const isRequestCancelButton = document.getElementById('requestCancel');
if(isRequestCancelButton){
	console.log('Event(requestCancel) activate OK');
	document.getElementById('requestCancel').addEventListener('click', async () => {
		const account = await getAccount();
	
		if(loginUserWalletAddress == account){
			const productId = document.getElementById('requestCancelValue').value;
			contract.methods.requestCancel(productId).send({ from: account })
				.on('receipt', function(receipt){
					console.log('Cancellation requested:', receipt);
					updateProductState3(productId);
				})
				.on('error', function(error) {
					console.error('Transaction failed:', error)
					window.location.href = '/';
				});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
};

// 판매자가 구매자의 거래 취소 요청을 승인
const isApproveCancelButton = document.getElementById('approveCancel');
if(isApproveCancelButton){
	console.log('Event(approveCancel) activate OK');
	document.getElementById('approveCancel').addEventListener('click', async () => {
		const account = await getAccount();
		
		if(loginUserWalletAddress == account){
			const productId = document.getElementById('approveCancelValue').value;
			contract.methods.approveCancel(productId).send({ from: account })
				.on('receipt', function(receipt){
					console.log('Cancellation approved:', receipt);
					updateProductState0(productId);
				})
				.on('error', function(error) {
					console.error('Transaction failed:', error)
					window.location.href = '/';
				});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
};

// 판매자가 거래를 취소
const isSellerCancelButton = document.getElementById('sellerCancel');
if(isSellerCancelButton){
	console.log('Event(sellerCancel) activate OK');
	document.getElementById('sellerCancel').addEventListener('click', async () => {
		const account = await getAccount();
	
		if(loginUserWalletAddress == account){
			const productId = document.getElementById('sellerCancelValue').value;
			contract.methods.sellerCancel(productId).send({ from: account })
			.on('receipt', function(receipt){
				console.log('Cancellation approved:', receipt);
				updateProductState0(productId);
			})
			.on('error', function(error) {
				console.error('Transaction failed:', error)
				window.location.href = '/';
			});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
};

// --- 클래스 관리 파트

// 구매자가 판매자에게 거래 취소를 요청
document.querySelectorAll('.requestCancelButton').forEach(button => {
	button.addEventListener('click', async ()=> {
		const productId = this.getAttribute('data-product-id');
		const account = await getAccount();

		if(loginUserWalletAddress == account){
			contract.methods.requestCancel(productId).send({ from: account })
			.on('receipt', function(receipt){
				console.log('Purchase confirmed:', receipt);
				updateProductState3(productId);
			})
			.on('error', function(error) {
				console.error('Transaction failed:', error);
				window.location.href = '/';
			});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
});

// 구매자의 구매 확정
document.querySelectorAll('.confirmPurchaseButton').forEach(button => {
	button.addEventListener('click', async ()=> {
		const productId = this.getAttribute('data-product-id');
		const account = await getAccount();

		if(loginUserWalletAddress == account){
			contract.methods.confirmPurchase(productId).send({ from: account })
			.on('receipt', function(receipt){
				console.log('Purchase confirmed:', receipt);
				updateProductState2(productId);
			})
			.on('error', function(error) {
				console.error('Transaction failed:', error);
				window.location.href = '/';
			});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
});

// 판매자가 구매자의 거래 취소 요청을 승인
document.querySelectorAll('.approveCancelButton').forEach(button => {
	button.addEventListener('click', async ()=> {
		const productId = this.getAttribute('data-product-id');
		const account = await getAccount();

		if(loginUserWalletAddress == account){
			contract.methods.approveCancel(productId).send({ from: account })
			.on('receipt', function(receipt){
				console.log('Cancellation approved:', receipt);
				updateProductState0(productId);
			})
			.on('error', function(error) {
				console.error('Transaction failed:', error);
				window.location.href = '/';
			});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
});

// 판매자가 거래를 취소
document.querySelectorAll('.sellerCancelButton').forEach(button => {
	button.addEventListener('click', async ()=> {
		const productId = this.getAttribute('data-product-id');
		const account = await getAccount();

		if(loginUserWalletAddress == account){
			contract.methods.sellerCancel(productId).send({ from: account })
			.on('receipt', function(receipt){
				console.log('Cancellation approved:', receipt);
				updateProductState0(productId);
			})
			.on('error', function(error) {
				console.error('Transaction failed:', error);
				window.location.href = '/';
			});
		}else{
			alert("MetaMask의 지갑 주소가 로그인 사용자의 지갑 주소와 일치하지 않습니다.");
			console.log('>>> loginUserWalletAddress >>> ', loginUserWalletAddress);
			console.log('>>> account >>> ', account);
		};
	});
});

// --- Product State Update 비동기통신 파트

async function updateProductState0(productId){
	fetch('/product/updateState0/' + productId)
	.then(response => {
		if(response > 0){
			console.log('update success');
		}
	})
	.catch((error) => {
		console.error('update fail', error);
	})
};

async function updateProductState1(productId){
	fetch('/product/updateState1/' + productId)
	.then(response => {
		if(response > 0){
			console.log('update success');
		}
	})
	.catch((error) => {
		console.error('update fail', error);
	})
};

async function updateProductState2(productId){
	fetch('/product/updateState2/' + productId)
	.then(response => {
		if(response > 0){
			console.log('update success');
		}
	})
	.catch((error) => {
		console.error('update fail', error);
	})
};

async function updateProductState3(productId){
	fetch('/product/updateState3/' + productId)
	.then(response => {
		if(response > 0){
			console.log('update success');
		}
	})
	.catch((error) => {
		console.error('update fail', error);
	})
};
