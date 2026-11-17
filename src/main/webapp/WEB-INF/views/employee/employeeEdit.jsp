<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">

    <title>HRMS - 내 정보 수정</title>
   

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/employeehome.css">
</head>
<body class="myedit-page">

<div class="myedit-layout">

    <aside class="myedit-sidebar">
        <div class="myedit-logo">▦ HRMS Employee</div>
        <div class="myedit-sidebar-title">인사관리 시스템</div>

        <nav class="myedit-menu">
            <a href="${pageContext.request.contextPath}/employee/home"
               class="myedit-menu-item active">
                01 내 정보
            </a>
            <span class="myedit-menu-item">02 근태 관리</span>
            <span class="myedit-menu-item">03 휴가 관리</span>
            <span class="myedit-menu-item">04 급여 조회</span>
        </nav>
    </aside>

    <div class="myedit-main">

        <header class="myedit-header">
            <div class="myedit-header-user" id="loginUser">
                로그인 사용자
            </div>
        </header>

        <main class="myedit-content">

            <div class="myedit-heading">
                <h1>내 정보 수정</h1>
                <p>본인의 연락처와 이메일을 직접 수정할 수 있습니다.</p>
            </div>

            <div class="myedit-notice">
                <strong>
                    ⓘ 회색 입력란은 수정할 수 없는 인사 정보입니다.
                </strong>
                <p>
                    성명·부서·직급·입사일 등 변경이 필요한 경우
                    인사 담당자에게 문의하세요.
                </p>
            </div>

            <form id="myInfoEditForm"
                  action="${pageContext.request.contextPath}/api/employees/me"
                  method="post"
                  novalidate>

                <section class="myedit-card">

                    <div class="myedit-card-heading">
                        <h2>기본 정보</h2>
                        <span class="myedit-status" id="statusBadge">
                            조회 전
                        </span>
                    </div>

                    <div class="myedit-grid">

                        <div class="myedit-field">
                            <label for="empId">사번 (변경 불가)</label>
                            <input type="text" id="empId"
                                   placeholder="사번" readonly>
                            <span class="myedit-lock">🔒</span>
                        </div>

                        <div class="myedit-field">
                            <label for="ename">성명 (변경 불가)</label>
                            <input type="text" id="ename"
                                   placeholder="성명" readonly>
                            <span class="myedit-lock">🔒</span>
                        </div>

                        <div class="myedit-field">
                            <label for="departmentName">부서 (변경 불가)</label>
                            <input type="text" id="departmentName"
                                   placeholder="부서" readonly>
                            <span class="myedit-lock">🔒</span>
                        </div>

                        <div class="myedit-field">
                            <label for="jobName">직급 (변경 불가)</label>
                            <input type="text" id="jobName"
                                   placeholder="직급" readonly>
                            <span class="myedit-lock">🔒</span>
                        </div>

                        <div class="myedit-field">
                            <label for="hiredate">입사일 (변경 불가)</label>
                            <input type="text" id="hiredate"
                                   placeholder="입사일" readonly>
                            <span class="myedit-lock">🔒</span>
                        </div>

                        <div class="myedit-field">
                            <label for="status">재직 상태 (변경 불가)</label>
                            <input type="text" id="status"
                                   placeholder="재직 상태" readonly>
                            <span class="myedit-lock">🔒</span>
                        </div>

                    </div>

                    <hr class="myedit-divider">

                    <h2 class="myedit-section-title">수정 가능한 정보</h2>

                    <div class="myedit-grid">

                        <div class="myedit-field">
                            <label for="email">이메일 *</label>
                            <input type="email"
                                   id="email"
                                   name="email"
                                   placeholder="example@hrms.com"
                                   maxlength="40"
                                   autocomplete="email"
                                   required>
                        </div>

                        <div class="myedit-field">
                            <label for="phone">연락처</label>
                            <input type="tel"
                                   id="phone"
                                   name="phone"
                                   placeholder="010-0000-0000"
                                   maxlength="13"
                                   autocomplete="tel">
                        </div>

                    </div>

                    <p id="myEditMessage"
                       class="myedit-message"
                       role="status"></p>

                </section>

                <section class="myedit-actions">

                    <div class="myedit-actions-text">
                        <strong>저장 전 확인</strong>
                        <p>
                            이메일 형식과 연락처 입력값을 확인 후 저장하세요.
                            변경된 정보는 본인 계정에 반영됩니다.
                        </p>
                    </div>

                    <div class="myedit-buttons">

                        <button type="button"
                                id="cancelMyEditBtn"
                                class="myedit-btn myedit-btn-outline">
                            취소
                        </button>

                        <button type="submit"
                                id="saveMyInfoBtn"
                                class="myedit-btn myedit-btn-primary">
                            변경사항 저장
                        </button>

                    </div>
                </section>

            </form>
        </main>
    </div>
</div>

<!-- 변경 확인 및 처리 결과 팝업 -->
<div id="myInfoModal" class="info-modal">
    <div class="info-modal-content">

        <div class="info-modal-title">
            <span id="infoModalIcon" class="info-modal-icon">?</span>
            <h3 id="infoModalTitle"></h3>
        </div>

        <p id="infoModalMessage"></p>

        <div class="info-modal-description"
             id="infoModalDescription"></div>

        <div class="info-modal-buttons">

            <button type="button"
                    id="infoModalCancel"
                    class="info-modal-secondary">
                취소
            </button>

            <button type="button"
                    id="infoModalConfirm"
                    class="info-modal-primary">
                확인
            </button>

        </div>
    </div>
</div>

<script>
(function () {
    const contextPath = "${pageContext.request.contextPath}";
    const byId = id => document.getElementById(id);

    let infoSaving = false;

    async function requestApi(path, method = "GET", body) {
        const headers = {
            "Accept": "text/plain, application/json"
        };

        const options = {
            method: method,
            credentials: "same-origin",
            headers: headers
        };

        if (body !== undefined) {
            headers["Content-Type"] =
                "application/json; charset=UTF-8";

            options.body = JSON.stringify(body);
        }

        if (!/^(GET|HEAD|OPTIONS)$/i.test(method)) {
            const token =
                document.querySelector('meta[name="_csrf"]').content;

            const header =
                document.querySelector('meta[name="_csrf_header"]').content;

            if (token && header) {
                headers[header] = token;
            }
        }

        let response;

        try {
            response = await fetch(contextPath + path, options);
        } catch (error) {
            throw new Error("서버와의 통신 중 오류가 발생했습니다.");
        }

        if (response.status === 401 || response.redirected) {
            throw new Error(
                "로그인 정보가 만료되었습니다. 다시 로그인해 주세요."
            );
        }

        if (response.status === 403) {
            throw new Error(
                "요청이 거부되었습니다. 페이지를 새로고침한 뒤 다시 시도해 주세요."
            );
        }

        const contentType =
            response.headers.get("content-type") || "";

        const text = await response.text();
        let data = null;

        if (contentType.includes("json")) {
            try {
                data = JSON.parse(text);
            } catch (ignored) {
            }
        }

        if (!response.ok || (data && data.success === false)) {
            let message =
                "요청 처리에 실패했습니다. (HTTP " +
                response.status + ")";

            if (
                data &&
                typeof data.message === "string" &&
                data.message
            ) {
                message = data.message;

            } else if (
                contentType.includes("text/plain") &&
                text.trim()
            ) {
                message = text;
            }

            throw new Error(message);
        }

        if (contentType.includes("text/html")) {
            throw new Error(
                "서버에서 예상하지 못한 화면을 반환했습니다. 요청 주소와 로그인 상태를 확인해 주세요."
            );
        }

        return data !== null ? data : text;
    }

    // 내 정보 조회
    async function loadMyEditInfo() {
        try {
            const emp = await requestApi("/api/employees/me");

            if (
                !emp ||
                typeof emp !== "object" ||
                Array.isArray(emp)
            ) {
                throw new Error("사원 정보 응답을 확인할 수 없습니다.");
            }

            byId("loginUser").textContent =
                (emp.ename || "사용자") + " 사원";

            const status = {
                ACTIVE: "재직",
                LEAVE: "휴직",
                RETIRED: "퇴직"
            }[emp.status] || "-";

            byId("empId").value = emp.empId ?? "";
            byId("ename").value = emp.ename ?? "";

            byId("departmentName").value =
                emp.department?.departmentName ?? "";

            byId("jobName").value =
                emp.job?.jobName ?? "";

            byId("hiredate").value = emp.hiredate
                ? new Date(emp.hiredate).toLocaleDateString("ko-KR")
                : "";

            byId("status").value = status;

            byId("statusBadge").textContent =
                status === "재직" ? "재직 중" : status;

            byId("email").value = emp.email ?? "";
            byId("phone").value = emp.phone ?? "";

        } catch (error) {
            byId("myEditMessage").textContent = error.message;
        }
    }

    // 공통 팝업
    function showInfoModal({
        type = "confirm",
        title,
        message,
        description = "",
        cancelText = "취소",
        confirmText = "확인",
        onCancel,
        onConfirm
    }) {
        const modal = byId("myInfoModal");
        const icon = byId("infoModalIcon");
        const cancelBtn = byId("infoModalCancel");
        const confirmBtn = byId("infoModalConfirm");

        icon.textContent = {
            confirm: "?",
            success: "✓",
            warning: "!",
            error: "!"
        }[type];

        icon.className = "info-modal-icon " + type;

        byId("infoModalTitle").textContent = title;
        byId("infoModalMessage").textContent = message;
        byId("infoModalDescription").textContent = description;

        cancelBtn.textContent = cancelText;
        confirmBtn.textContent = confirmText;

        cancelBtn.style.display =
            type === "error" ? "none" : "";

        modal.classList.add("show");

        cancelBtn.onclick = () => {
            modal.classList.remove("show");

            if (onCancel) {
                onCancel();
            }
        };

        confirmBtn.onclick = () => {
            modal.classList.remove("show");

            if (onConfirm) {
                onConfirm();
            }
        };
    }

    // 이메일·연락처 실제 저장
    async function saveMyInfo(email, phone) {
        if (infoSaving) {
            return;
        }

        infoSaving = true;
        byId("saveMyInfoBtn").disabled = true;

        try {
            await requestApi(
                "/api/employees/me",
                "PUT",
                { email, phone }
            );

            showInfoModal({
                type: "success",
                title: "내 정보 수정 완료",
                message: "변경이 완료되었습니다.",
                description: "변경된 정보는 내 정보 화면에 반영됩니다.",
                cancelText: "닫기",
                confirmText: "내 정보 보기",
                onCancel: () => location.reload(),
                onConfirm: () => {
                    location.href = contextPath + "/employee/home";
                }
            });

        } catch (error) {
            showInfoModal({
                type: "error",
                title: "정보 수정 실패",
                message: "정보를 저장하지 못했습니다.",
                description: error.message
            });

        } finally {
            infoSaving = false;
            byId("saveMyInfoBtn").disabled = false;
        }
    }

    // 이메일·연락처 변경 전 확인
    byId("myInfoEditForm").addEventListener("submit", event => {
        event.preventDefault();

        if (infoSaving) {
            return;
        }

        const email = byId("email").value.trim();
        const phone = byId("phone").value.trim();

        if (
            !/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email) ||
            email.length > 40
        ) {
            showInfoModal({
                type: "error",
                title: "입력 정보 확인",
                message: "이메일 형식이 올바르지 않습니다.",
                description: "이메일 주소를 다시 확인해 주세요."
            });
            return;
        }

        if (phone && !/^010-\d{4}-\d{4}$/.test(phone)) {
            showInfoModal({
                type: "error",
                title: "입력 정보 확인",
                message: "연락처 형식이 올바르지 않습니다.",
                description: "010-0000-0000 형식으로 입력해 주세요."
            });
            return;
        }

        showInfoModal({
            type: "confirm",
            title: "내 정보 변경 확인",
            message: "변경하시겠습니까?",
            description: "본인 계정의 연락처 정보만 변경됩니다.",
            confirmText: "변경사항 저장",
            onConfirm: () => saveMyInfo(email, phone)
        });
    });

    // 수정 취소
    byId("cancelMyEditBtn").addEventListener("click", () => {
        showInfoModal({
            type: "warning",
            title: "변경사항 취소",
            message: "저장하지 않은 변경사항이 있습니다.",
            description: "이 화면에서 나가면 변경 내용이 사라집니다.",
            cancelText: "계속 수정",
            confirmText: "변경 취소",
            onConfirm: () => {
                location.href = contextPath + "/employee/home";
            }
        });
    });

    if (document.readyState === "loading") {
        document.addEventListener(
            "DOMContentLoaded",
            loadMyEditInfo
        );
    } else {
        loadMyEditInfo();
    }
}());
</script>

</body>
</html>