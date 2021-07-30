package com.smali.ebics.client.h005;

import com.smali.ebics.client.ebics.BTFService;
import com.smali.ebics.client.ebics.EbicsConfig;
import com.smali.ebics.client.ebics.FormWrapper;

import org.ebics.client.filetransfer.h005.FileTransfer;
import org.ebics.client.io.IOUtils;
import org.ebics.client.keymgmt.h005.KeyManagementImpl;
import org.ebics.client.order.EbicsService;
import org.ebics.client.order.h005.EbicsUploadOrder;
import org.ebics.client.session.EbicsSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/h005")
public class H005Controller {

	@PostMapping(path = "/sendINI")
	public ResponseEntity<String> sendINI() {
		EbicsConfig ebicsConfig = EbicsConfig.getInstance();
		try {
			ebicsConfig.loadDefaultUser();

			EbicsSession session = ebicsConfig.getEbicsModel().createSession(ebicsConfig.getDefaultUser(),
					ebicsConfig.getDefaultProduct());

			KeyManagementImpl keyObject = new KeyManagementImpl(session);
			return ResponseEntity.ok().body(keyObject.sendINI(null));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	@PostMapping(path = "/sendHIA")
	public ResponseEntity<String> sendHIA() {
		EbicsConfig ebicsConfig = EbicsConfig.getInstance();
		try {
			ebicsConfig.loadDefaultUser();

			EbicsSession session = ebicsConfig.getEbicsModel().createSession(ebicsConfig.getDefaultUser(),
					ebicsConfig.getDefaultProduct());

			KeyManagementImpl keyObject = new KeyManagementImpl(session);
			return ResponseEntity.ok().body(keyObject.sendHIA(null));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());

		}
	}

	@PostMapping(path = "/sendHPB")
	public ResponseEntity<String> sendHPB() {
		EbicsConfig ebicsConfig = EbicsConfig.getInstance();
		try {
			ebicsConfig.loadDefaultUser();

			EbicsSession session = ebicsConfig.getEbicsModel().createSession(ebicsConfig.getDefaultUser(),
					ebicsConfig.getDefaultProduct());

			KeyManagementImpl keyObject = new KeyManagementImpl(session);
			return ResponseEntity.ok().body(keyObject.sendHPB());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());

		}
	}

	@PostMapping(path = "/sendSPR")
	public ResponseEntity<String> sendSPR() {
		EbicsConfig ebicsConfig = EbicsConfig.getInstance();
		try {
			ebicsConfig.loadDefaultUser();

			EbicsSession session = ebicsConfig.getEbicsModel().createSession(ebicsConfig.getDefaultUser(),
					ebicsConfig.getDefaultProduct());

			KeyManagementImpl keyObject = new KeyManagementImpl(session);
			return ResponseEntity.ok().body(keyObject.lockAccess());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());

		}
	}

	@PostMapping(path = "/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
			@RequestParam("serviceName") String serviceName) {
		EbicsConfig ebicsConfig = EbicsConfig.getInstance();
		try {
			ebicsConfig.loadDefaultUser();
			EbicsSession session = ebicsConfig.getEbicsModel().createSession(ebicsConfig.getDefaultUser(),
					ebicsConfig.getDefaultProduct());
			FileTransfer transferManager = new FileTransfer(session);
			EbicsUploadOrder ebicsUploadOrder = new EbicsUploadOrder(new BTFService(), false, false,
					file.getOriginalFilename(), null);
			String result = transferManager.sendFile(file.getBytes(), ebicsUploadOrder);
			return ResponseEntity.ok().body(result);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body(e.getMessage());

		}
	}

}
