import { Link, useNavigate } from "react-router-dom";

const VoucherComponent = (props) => {
  const { voucher } = props;
  return (
    <>
      <div class="container mt-5">
        <div class="d-flex justify-content-center row">
          <div class="col-md-10 col-lg-10">
            <h1 style={{ color: "green" }}>
              Congratulation !! You have won voucher below
            </h1>
            <div class="border">
              <div class="question bg-success p-3 border-bottom">
                <div class="d-flex flex-row justify-content-between align-items-center ">
                  <h4 style={{ color: "white" }}>Voucher Information</h4>
                </div>
              </div>
              <div class="question bg-white p-3 border-bottom">
                <div class="d-flex flex-row align-items-center question-title">
                  <h3 class="text-danger">Code:&nbsp;&nbsp;&nbsp;</h3>
                  <h5 class="mt-1 ml-2">{voucher?.voucherCode}</h5>
                </div>
                <div class="d-flex flex-row align-items-center question-title">
                  <h3 class="text-danger">Name:&nbsp;&nbsp;&nbsp;</h3>
                  <h5 class="mt-1 ml-2">{voucher?.voucherName}</h5>
                </div>
                <div class="d-flex flex-row align-items-center question-title">
                  <h3 class="text-danger">Description:&nbsp;&nbsp;&nbsp;</h3>
                  <h5 class="mt-1 ml-2">{voucher?.description}</h5>
                </div>
              </div>
              <div class="d-flex flex-row justify-content-between align-items-center p-3 bg-white">
                <Link
                  className="btn btn-info "
                  to={`/voucher-detail/${voucher?.voucherId}`}
                >
                  Go to your voucher
                </Link>
                <Link className="btn btn-danger " to={"/campaign"}>
                  Back
                </Link>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default VoucherComponent;
